output "vpc" {
  description = "VPC name that the EKS cluster is using"
  value       = module.vpc.name
}

output "vpc_id" {
  description = "VPC ID that the EKS cluster is using"
  value       = module.vpc.vpc_id
}

output "configure_kubectl" {
  description = "Configure kubectl: make sure you're logged in with the correct AWS profile and run the following command to update your kubeconfig"
  value       = "aws eks --region ${var.deploy_region} update-kubeconfig --name ${module.eks.cluster_name}"
}

output "cluster_name" {
  description = "Cluster name of the EKS cluster"
  value       = module.eks.cluster_name
}

# output "vpc_database_subnets" {
#   value = module.vpc.database_subnets
# }
# output "is_restored_from_snapshot" {
#   description = "Whether the DB was restored from snapshot"
#   value       = var.restore_from_snapshot
# }
# output "ecr_repository_url" {
#   value = data.aws_ecr_repository.existing_repo.repository_url
# }

# output "karpenter_iam_role_arn" {
#   description = "The IAM role ARN for Karpenter"
#   value       = module.karpenter.iam_role_arn
# }

# output "karpenter_instance_profile_name" {
#   description = "IAM Instance Profile Name for Karpenter Nodes"
#   value       = data.aws_iam_instance_profile.karpenter.name
# }


# output "karpenter_helm_release_name" {
#   description = "The name of the Karpenter Helm release"
#   value       = helm_release.karpenter.name
# }

# output "karpenter_provisioner_yaml" {
#   description = "The YAML definition for the Karpenter Provisioner"
#   value       = kubectl_manifest.karpenter_provisioner.yaml_body
#   sensitive   = true
# }