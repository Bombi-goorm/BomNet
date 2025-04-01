# Karpenter default EC2NodeClass and NodePool

resource "kubectl_manifest" "karpenter_default_ec2_node_class" {
  yaml_body = <<YAML
apiVersion: karpenter.k8s.aws/v1
kind: EC2NodeClass
metadata:
  name: default
spec:
  role: ${local.node_iam_role_name}
  amiSelectorTerms: 
  - alias: al2@latest
  securityGroupSelectorTerms:
  - tags:
      karpenter.sh/discovery: ${var.project_name}
  subnetSelectorTerms:
  - tags:
      karpenter.sh/discovery: ${var.project_name}
  tags:
    IntentLabel: apps
    KarpenterNodePoolName: default
    NodeType: default
    intent: apps
    karpenter.sh/discovery: ${var.project_name}
    project: karpenter-blueprints
YAML
  depends_on = [
    module.eks.cluster,
    module.eks_blueprints_addons.karpenter,
  ]
}

resource "kubectl_manifest" "karpenter_default_node_pool" {
  yaml_body = <<YAML
apiVersion: karpenter.sh/v1
kind: NodePool
metadata:
  name: default 
spec:  
  template:
    metadata:
      labels:
        intent: apps
    spec:
      requirements:
        - key: kubernetes.io/arch
          operator: In
          values: ["amd64"]
        - key: "karpenter.k8s.aws/instance-cpu"
          operator: In
          values: ["2", "4", "8", "16", "32", "48", "64"]
        - key: karpenter.sh/capacity-type
          operator: In
          values: ["spot", "on-demand"]
        - key: karpenter.k8s.aws/instance-category
          operator: In
          values: ["m", "t"]
      nodeClassRef:
        name: default
        group: karpenter.k8s.aws
        kind: EC2NodeClass
      kubelet:
        containerRuntime: containerd
        systemReserved:
          cpu: 100m
          memory: 100Mi
  disruption:
    consolidationPolicy: WhenEmptyOrUnderutilized
    consolidateAfter: 1m
    
YAML
  depends_on = [
    module.eks.cluster,
    module.eks_blueprints_addons.karpenter,
    kubectl_manifest.karpenter_default_ec2_node_class,
  ]
}

# Elasticsearch용 EC2NodeClass
resource "kubectl_manifest" "karpenter_monitoring_ec2_node_class" {
  yaml_body = <<YAML
apiVersion: karpenter.k8s.aws/v1
kind: EC2NodeClass
metadata:
  name: monitoring
spec:
  role: ${local.node_iam_role_name}
  amiSelectorTerms: 
  - alias: al2@latest
  securityGroupSelectorTerms:
  - tags:
      karpenter.sh/discovery: ${var.project_name}
  subnetSelectorTerms:
  - tags:
      karpenter.sh/discovery: ${var.project_name}
  tags:
    IntentLabel: monitoring
    KarpenterNodePoolName: monitoring
    NodeType: monitoring
    intent: monitoring
    karpenter.sh/discovery: ${var.project_name}
    project: karpenter-blueprints
YAML
  depends_on = [
    module.eks.cluster,
    module.eks_blueprints_addons.karpenter,
  ]
}

# Elasticsearch용 NodePool
resource "kubectl_manifest" "karpenter_monitoring_node_pool" {
  yaml_body = <<YAML
apiVersion: karpenter.sh/v1
kind: NodePool
metadata:
  name: monitoring
spec:
  template:
    metadata:
      labels:
        intent: monitoring
    spec:
      requirements:
        - key: kubernetes.io/arch
          operator: In
          values: ["amd64"]
        - key: "node.kubernetes.io/instance-type"
          operator: In
          values: ["m5.large"]
        - key: karpenter.sh/capacity-type
          operator: In
          values: ["on-demand"]
      nodeClassRef:
        name: monitoring
        group: karpenter.k8s.aws
        kind: EC2NodeClass
      taints:
        - key: workload-type
          value: monitoring
          effect: NoSchedule
      kubelet:
        containerRuntime: containerd
        systemReserved:
          cpu: 100m
          memory: 100Mi
  disruption:
    consolidationPolicy: WhenEmptyOrUnderutilized
    consolidateAfter: 1m
YAML
  depends_on = [
    module.eks.cluster,
    module.eks_blueprints_addons.karpenter,
    kubectl_manifest.karpenter_monitoring_ec2_node_class,
  ]
}