locals {
  node_iam_role_name = module.eks_blueprints_addons.karpenter.node_iam_role_name

  tags = {
    blueprint = var.project_name
  }
}
