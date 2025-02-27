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

      subnet_ids   = module.vpc.private_subnets
      max_size     = 3
      desired_size = 2
      min_size     = 2

      # Launch template configuration
      create_launch_template = true # false will use the default launch template

      labels = {
        intent = "control-apps"
      }
    }
  }

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