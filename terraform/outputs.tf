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

output "vpc_database_subnets" {
  value = module.vpc.database_subnets
}
output "is_restored_from_snapshot" {
  description = "Whether the DB was restored from snapshot"
  value       = var.restore_from_snapshot
}
# output "ecr_repository_url" {
#   value = data.aws_ecr_repository.existing_repo.repository_url
# }