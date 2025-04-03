# Security Group for AWS RDS DB
module "rdsdb_sg" {
  source  = "terraform-aws-modules/security-group/aws"
  version = "5.1.0"

  name        = "rdsdb-sg"
  description = "Access to MariaDB for entire VPC CIDR Block"
  vpc_id      = module.vpc.vpc_id

  ingress_with_cidr_blocks = [
    {
      from_port   = 3306
      to_port     = 3306
      protocol    = "tcp"
      description = "MariaDB access from within VPC"
      cidr_blocks = module.vpc.vpc_cidr_block
    },
  ]

  egress_rules = ["all-all"]
  depends_on = [module.vpc]
}

# RDS Module
module "db" {
  source  = "terraform-aws-modules/rds/aws"
  version = "5.1.0"

  identifier = "bombi"

  # Engine options
  engine               = "mariadb"
  engine_version       = "10.6"
  instance_class      = "db.t4g.micro"
  family              = "mariadb10.6"
  allocated_storage   = 20

  # Credentials
  db_name  = var.restore_from_snapshot ? null : var.db_name
  username = var.restore_from_snapshot ? null : var.db_username
  password = var.restore_from_snapshot ? null : var.db_password
  port     = 3306

  # 스냅샷 설정
  snapshot_identifier = var.restore_from_snapshot ? var.snapshot_identifier : null

  # Network
  vpc_security_group_ids = [module.rdsdb_sg.security_group_id]
  subnet_ids             = module.vpc.database_subnets
  create_db_subnet_group = true
  db_subnet_group_name  = "${var.project_name}-db-subnet-group"

  # Option Group - 외부 모듈 사용
  create_db_option_group = false
  option_group_name     = module.db_option_group.db_option_group_id

  # Parameter Group
  create_db_parameter_group = true
  parameter_group_name     = "${var.project_name}-parameter-group"
  parameter_group_use_name_prefix = false
  parameters = [
    {
      name  = "character_set_client"
      value = "utf8mb4"
    },
    {
      name  = "character_set_server"
      value = "utf8mb4"
    }
  ]

  # Backup 설정
  # backup_retention_period = 7
  # backup_window          = "03:00-06:00"
  # maintenance_window     = "Mon:00:00-Mon:03:00"
  
  # 삭제 시 스냅샷 생성
  skip_final_snapshot     = false
  final_snapshot_identifier_prefix = "${var.project_name}-final" 

  tags = {
    Name        = "${var.project_name}-rds"
    RestoredFromSnapshot = var.restore_from_snapshot ? "true" : "false"
    SnapshotID = var.restore_from_snapshot ? var.snapshot_identifier : "none"
  }

  depends_on = [module.vpc, module.rdsdb_sg, module.db_option_group]
}

module "db_option_group" {
  source = "terraform-aws-modules/rds/aws//modules/db_option_group"
  version = "6.3.0"

  name                = "${var.project_name}-option-group"
  use_name_prefix     = false
  option_group_description = "Option group for ${var.project_name}"

  engine_name         = "mariadb"
  major_engine_version = "10.6"

  options = [
    {
      option_name = "MARIADB_AUDIT_PLUGIN"
      option_settings = [
        {
          name  = "SERVER_AUDIT_EVENTS"
          value = "CONNECT"
        }
      ]
    }
  ]

  tags = {
    Name = "${var.project_name}-option-group"
  }
}

# ConfigMap
resource "kubernetes_config_map" "db_config" {
  metadata {
    name = "db-config"
  }

  data = {
    DATABASE_HOST = module.db.db_instance_address
    DATABASE_NAME = var.db_name
  }
}