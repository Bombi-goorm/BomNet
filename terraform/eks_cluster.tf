# PROVIDER
provider "aws" {
  region = var.deploy_region
  alias  = "seoul"
}


provider "aws" {
  alias  = "virginia"
  region = "us-east-1"
}

provider "kubernetes"  {
  host                   = module.eks.cluster_endpoint
  cluster_ca_certificate = base64decode(module.eks.cluster_certificate_authority_data)
  token                  = data.aws_eks_cluster_auth.this.token
}

provider "helm" {
  kubernetes {
    host                   = module.eks.cluster_endpoint
    cluster_ca_certificate = base64decode(module.eks.cluster_certificate_authority_data)
    token                  = data.aws_eks_cluster_auth.this.token
  }
}

provider "kubectl" {
  apply_retry_count      = 10
  host                   = module.eks.cluster_endpoint
  cluster_ca_certificate = base64decode(module.eks.cluster_certificate_authority_data)
  load_config_file       = false
  token                  = data.aws_eks_cluster_auth.this.token
}

# EKS Cluster
module "eks" {
  source  = "terraform-aws-modules/eks/aws"
  version = "20.24.0"

  cluster_name                   = var.project_name
  cluster_version                = var.eks_cluster_version
  cluster_endpoint_public_access = true

  cluster_addons = {
    kube-proxy = { most_recent = true }
    coredns    = { most_recent = true }

    vpc-cni = {
      most_recent    = true
      before_compute = true
      configuration_values = jsonencode({
        env = {
          ENABLE_PREFIX_DELEGATION = "true"
          WARM_PREFIX_TARGET       = "1"
        }
      })
    }
  }

  vpc_id     = module.vpc.vpc_id
  subnet_ids = module.vpc.private_subnets

  create_cloudwatch_log_group              = false
  create_cluster_security_group            = false
  create_node_security_group               = false
  authentication_mode                      = "API_AND_CONFIG_MAP"
  enable_cluster_creator_admin_permissions = true
  cluster_security_group_id = aws_security_group.eks_cluster.id
  node_security_group_additional_rules = {
    ingress_allow_access_from_control_plane = {
      type                          = "ingress"
      protocol                      = "tcp"
      from_port                     = 9443
      to_port                       = 9443
      source_cluster_security_group = true
      description                   = "Allow access from control plane to webhook port of AWS load balancer controller"
    }
    ingress_cluster_all = {
      description                   = "Cluster to node all ports/protocols"
      protocol                      = "-1"
      from_port                     = 0
      to_port                       = 0
      type                          = "ingress"
      source_cluster_security_group = true
    }
  }

  eks_managed_node_groups = {
    managed_nodes = {
      node_group_name       = var.node_group_name
      instance_types        = var.eks_managed_nodes_instance_types
      capacity_type         = var.eks_managed_nodes_capacity_type
      create_security_group = false
      vpc_security_group_ids = [aws_security_group.eks_nodes.id]
      subnet_ids   = module.vpc.private_subnets
      max_size     = 3
      desired_size = 2
      min_size     = 2

      # Launch template configuration
      create_launch_template = true # false will use the default launch template

      labels = {
        intent = "control-apps"
        "node-groups" = "app"
      }
    }
  additional_nodes = {
    node_group_name       = "additional-node-group"
    instance_types        = ["m5.large"]
    capacity_type         = "ON_DEMAND"
    create_security_group = false
    vpc_security_group_ids = [aws_security_group.eks_nodes.id]
    subnet_ids   = module.vpc.private_subnets
    max_size     = 2
    desired_size = 1
    min_size     = 1

    create_launch_template = true

    labels = {
      intent = "monitoring"
      "node-groups" = "monitoring"
    }
  }

  }
  tags = merge(local.tags, {
    "karpenter.sh/discovery" = var.project_name
  })

  depends_on = [
    module.vpc.vpc_id
  ]
}

module "ebs_csi_driver_irsa" {
  source  = "terraform-aws-modules/iam/aws//modules/iam-role-for-service-accounts-eks"
  version = "5.44.0"

  role_name = "${module.eks.cluster_name}-ebs-csi-controller-sa"

  attach_ebs_csi_policy = true

  oidc_providers = {
    main = {
      provider_arn               = module.eks.oidc_provider_arn
      namespace_service_accounts = ["kube-system:ebs-csi-controller-sa"]
    }
  }

  depends_on = [
    module.eks.cluster_id
  ]
}

module "lb_role" {
  source = "terraform-aws-modules/iam/aws//modules/iam-role-for-service-accounts-eks"
  version = "5.20.0"

  role_name = "${var.project_name}-lb-controller"
  attach_load_balancer_controller_policy = true

  oidc_providers = {
    main = {
      provider_arn               = module.eks.oidc_provider_arn
      namespace_service_accounts = ["kube-system:aws-load-balancer-controller"]
    }
  }
  depends_on = [ module.eks ]
}

# AWS Load Balancer Controller 설치
resource "helm_release" "aws_load_balancer_controller" {
  name       = "aws-load-balancer-controller"
  repository = "https://aws.github.io/eks-charts"
  chart      = "aws-load-balancer-controller"
  namespace  = "kube-system"
  version    = "1.6.0"  

  set {
    name  = "clusterName"
    value = module.eks.cluster_name
  }

  set {
    name  = "serviceAccount.create"
    value = "true"
  }

  set {
    name  = "serviceAccount.name"
    value = "aws-load-balancer-controller"
  }

  set {
    name  = "serviceAccount.annotations.eks\\.amazonaws\\.com/role-arn"
    value = module.lb_role.iam_role_arn
  }

  set {
    name  = "region"
    value = var.deploy_region
  }

  set {
    name  = "vpcId"
    value = module.vpc.vpc_id
  }

  depends_on = [

    module.eks
  ]
}

# Secret Manager
resource "kubernetes_cluster_role" "admin" {
  metadata {
    name = "kubernetes-admin"
  }

  rule {
    api_groups = [""]
    resources  = ["secrets", "configmaps"]
    verbs      = ["create", "get", "list", "update", "delete"]
  }
}

resource "kubernetes_cluster_role_binding" "admin_binding" {
  metadata {
    name = "grant-secrets-configmaps-access"
  }

  subject {
    kind      = "User"
    name      = "arn:aws:iam::575108933149:user/hansususu"
    api_group = "rbac.authorization.k8s.io"
  }

  role_ref {
    kind     = "ClusterRole"
    name = kubernetes_cluster_role.admin.metadata[0].name
    api_group = "rbac.authorization.k8s.io"
  }
}


# External Secret 
# Helm Repo 설치
resource "helm_release" "external_secrets" {
  name       = "external-secrets"
  repository = "https://charts.external-secrets.io"
  chart      = "external-secrets"
  namespace  = "default" # 원하는 namespace 설정

  version = "0.7.0"  # 원하는 버전으로 변경 가능

  set {
    name  = "serviceAccount.create"
    value = "true"  # ServiceAccount 자동 생성
  }

  set {
    name  = "serviceAccount.name"
    value = "external-secrets-sa"  # 원하는 ServiceAccount 이름
  }

  set {
    name  = "controller.env.AWS_REGION"
    value = var.deploy_region # AWS 리전 설정
  }

  depends_on = [
    module.eks  # EKS 모듈이 먼저 배포되어야 함
  ]
}

# AWS Credentials
resource "kubernetes_secret" "aws_credentials" {
  metadata {
    name      = "aws-credentials"
    namespace = "default"
  }

  data = {
    "access-key-id"     = var.aws_access_key_id
    "secret-access-key" = var.aws_secret_access_key
  }
}

module "eks_blueprints_addons" {
  source  = "aws-ia/eks-blueprints-addons/aws"
  version = "1.16.3"

  cluster_name      = module.eks.cluster_name
  cluster_endpoint  = module.eks.cluster_endpoint
  cluster_version   = module.eks.cluster_version
  oidc_provider_arn = module.eks.oidc_provider_arn

  create_delay_dependencies = [for prof in module.eks.eks_managed_node_groups : prof.node_group_arn]

  # metric server
  enable_metrics_server = true

  eks_addons = {
    aws-ebs-csi-driver = {
      most_recent              = true
      service_account_role_arn = module.ebs_csi_driver_irsa.iam_role_arn
    }
  }

  # Karpenter
  enable_karpenter = true
  karpenter = {
    chart_version       = var.karpenter_chart_version
    repository_username = data.aws_ecrpublic_authorization_token.token.user_name
    repository_password = data.aws_ecrpublic_authorization_token.token.password
    timeout             = 600
  }
  karpenter_enable_spot_termination          = true
  karpenter_enable_instance_profile_creation = true
  karpenter_node = {
    iam_role_use_name_prefix = false
  }

  # Prometheus
  enable_kube_prometheus_stack = true
  kube_prometheus_stack = {
    chart         = "kube-prometheus-stack"
    chart_version = var.kube_prometheus_stack_chart_version
    repository    = "https://prometheus-community.github.io/helm-charts"
    namespace     = "monitoring"
    timeout       = 1200
  }

  depends_on = [
    module.eks.cluster_id
  ]
}



# Grafana 
resource "kubectl_manifest" "set_grafana_ingress_alb" {
  yaml_body = templatefile("${path.module}/alb/grafana.yml", {})

  depends_on = [
    module.eks.cluster_id,
    module.eks_blueprints_addons.kube_prometheus_stack,
  ]
}

resource "kubectl_manifest" "set_prometheus_ingress_alb" {
  yaml_body = templatefile("${path.module}/alb/prometheus.yml", {})

  depends_on = [
    module.eks.cluster_id,
  ]
}

resource "helm_release" "kube_ops_view" {
  name       = "kube-ops-view"
  repository = "https://geek-cookbook.github.io/charts/"
  chart      = "kube-ops-view"
  version    = "1.2.2" # 사용할 버전 지정

  namespace  = "monitoring" # kube-ops-view를 설치할 네임스페이스
  depends_on = [ module.eks, module.vpc ]
}

resource "kubernetes_ingress_v1" "kube_ops_ingress" {
  metadata {
    name = "kube-ops-ingress"
    namespace = "monitoring"
    annotations = {
      "alb.ingress.kubernetes.io/scheme" = "internet-facing"
      "alb.ingress.kubernetes.io/target-type" = "ip"
    }
  }

  spec {
    ingress_class_name = "alb"

    rule {
      http {
        path {
          path = "/"
          path_type = "Prefix"
          backend {
            service {
              name = "kube-ops-view"
              port {
                number = 8080
              }
            }
          }
        }
      }
    }
  }

  depends_on = [ helm_release.kube_ops_view, module.eks, module.vpc]
}