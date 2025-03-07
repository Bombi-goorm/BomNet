variable "project_name" {
  description = "Project name that the EKS cluster will use"
  type        = string
  default     = "eks-cluster"
}

variable "account_id" {
  description = "AWS Account ID"
  type        = string
}

variable "eks_cluster_version" {
  description = "Kubernetes `<major>.<minor>` version to use for the EKS cluster (i.e.: `1.27`)"
  type        = string
  default     = "1.30"
}

variable "node_group_name" {
  description = "Kubernetes node group name"
  type        = string
  default     = "managed-ondemand"
}

variable "vpc_cidr" {
  description = "CIDR for the VPC that the EKS cluster will use"
  type        = string
  default     = "10.0.0.0/16"
}

variable "deploy_region" {
  description = "The AWS region to deploy into (e.g. us-east-1)"
  type        = string
  default     = "ap-northeast-2"
}

variable "aws_alb_controller_name" {
  description = "AWS ALB controller name"
  type        = string
  default     = "aws-load-balancer-controller"
}

variable "aws_load_balancer_controller_image_tag" {
  description = "Desired AWS ALB Controller image tag to pull"
  type        = string
  default     = "v2.8.2"
}

variable "eks_managed_nodes_instance_types" {
  description = "Desired instance type(s) to use as worker node(s)"
  type        = list(string)
  default     = ["t3.medium", "t3a.medium"]
}

variable "eks_managed_nodes_capacity_type" {
  description = "Desired AWS ALB Controller image tag to pull"
  type        = string
  default     = "SPOT"
  validation {
    condition     = contains(["SPOT", "ON_DEMAND"], var.eks_managed_nodes_capacity_type)
    error_message = "Valid values for eks_managed_nodes_capacity_type are (SPOT, ON_DEMAND)"
  }
}

variable "kube_prometheus_stack_chart_version" {
  description = "Desired Kube Prometheus Stack Help chart version"
  type        = string
  default     = "62.6.0"
}

variable "karpenter_chart_version" {
  description = "Desired Karpenter Help chart version"
  type        = string
  default     = "1.0.1"
}

variable "external_secrets_service_account_name" {
  description = "external secrets addon service account name"
  type        = string
  default     = "external-secrets-sa"
}

variable "external_secrets_helm_chart_version" {
  description = "external secrets helm chart version"
  type        = string
  default     = "0.10.3"
}


# VPC
variable "default_instance_az" {
  description = "Allowed node availability zone"
  type        = list(string)
  default     = ["ap-northeast-2a", "ap-northeast-2c"]
}


# argocd
variable "argocd_deploy_name" {
  type = string
  default = "argocd"
}

variable "argocd_helm_chart_name" {
  type = string
  default = "argo-cd"
}

variable "argocd_helm_repo_url" {
  type = string
  default = "https://argoproj.github.io/argo-helm"
}

variable "argocd_target_namespace" {
  type = string
  default = "argocd"
}

variable "argocd_server_insecure" {
  type = bool
  default = true
}


# Required variables
variable "restore_from_snapshot" {
  description = "Whether to restore from snapshot"
  type        = bool
  default     = false
}

variable "snapshot_identifier" {
  description = "Snapshot identifier to restore from"
  type        = string
  default     = null
}

variable "db_username" {
  description = "Database username"
  type        = string
  sensitive   = true
}

variable "db_password" {
  description = "Database password"
  type        = string
  sensitive   = true
}

variable "db_name" {
  description = "Database name"
  type        = string
  default     = "bombi"
}