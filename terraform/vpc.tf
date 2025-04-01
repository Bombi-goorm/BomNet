# VPC
provider "aws" {
  region = var.deploy_region
}

module "vpc" {
  source  = "terraform-aws-modules/vpc/aws"
  version = "5.13.0"

  name = var.project_name
  cidr = var.vpc_cidr

  azs             = ["ap-northeast-2a", "ap-northeast-2c"]
  public_subnets = ["10.0.18.0/24", "10.0.19.0/24"]
  private_subnets = ["10.0.20.0/24", "10.0.21.0/24"]
  # database_subnets = ["10.0.22.0/24", "10.0.23.0/24"]

  create_igw              = true
  enable_nat_gateway      = true
  single_nat_gateway      = true
  enable_dns_hostnames    = true
  enable_dns_support      = true
  map_public_ip_on_launch = true
  # create_database_subnet_group = true
  # create_database_subnet_route_table = true

  # Manage so we can name
  manage_default_network_acl    = true
  default_network_acl_tags      = { Name = "${var.project_name}-default" }
  manage_default_route_table    = true
  default_route_table_tags      = { Name = "${var.project_name}-default" }
  manage_default_security_group = true
  default_security_group_tags   = { Name = "${var.project_name}-default" }

  public_subnet_tags = {
    "kubernetes.io/cluster/${var.project_name}" = "shared"
    "kubernetes.io/role/elb"                    = 1
  }

  private_subnet_tags = {
    "kubernetes.io/cluster/${var.project_name}" = "shared"
    "kubernetes.io/role/internal-elb"           = 1
    "karpenter.sh/discovery"                    = var.project_name
  }
}