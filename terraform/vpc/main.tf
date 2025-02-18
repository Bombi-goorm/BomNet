# vpc 정의
resource "aws_vpc" "this" {
    cidr_block = "10.50.0.0/16"
    enable_dns_hostnames = true
    enable_dns_support = true
    tags = {
        Name = "bombi-vpc"
    }
}

# IGW를 생성, vpc와 IGW 연결
resource "aws_internet_gateway" "this" {
  vpc_id = aws_vpc.this.id
  tags = {
    Name = "bombi-vpc-igw"
  }
}

# NATGW를 위한 eip 생성
resource "aws_eip" "this" {
    lifecycle {
        create_before_destroy = true 
        # 재성성시 먼저 새로운 리소스(eip)를 하나 만들고 기존 리소스를 삭제
        # 롤링업뎃이라고 생각하면 편하다
    }

    tags = {
        Name = "bombi-vpc-eip"

    }
}

# create public subnet
resource "aws_subnet" "pub_sub1" {
    vpc_id = aws_vpc.this.id #위에서 aws_vpc라는 리소스로 만든 this라는 리소스의 id
    cidr_block = "10.50.10.0/24"
    map_public_ip_on_launch = true
    enable_resource_name_dns_a_record_on_launch = true # 퍼블릭 아이피 자동 할당
    availability_zone = "ap-northeast-2a"
    tags = {
        Name = "pub-sub1"
        "kubernetes.io/cluster/pri-cluster" = "owned" 
        "kubernetes.io/role/elb" = "1"
    
    }
    depends_on = [ aws_internet_gateway.this ]
}

resource "aws_subnet" "pub_sub2" {
    vpc_id = aws_vpc.this.id #위에서 aws_vpc라는 리소스로 만든 this라는 리소스의 id
    cidr_block = "10.50.11.0/24"
    map_public_ip_on_launch = true
    enable_resource_name_dns_a_record_on_launch = true # 퍼블릭 아이피 자동 할당
    availability_zone = "ap-northeast-2c"
    tags = {
        Name = "pub-sub2"
        "kubernetes.io/cluster/pri-cluster" = "owned" 
        "kubernetes.io/role/elb" = "1" 
    
    }
    depends_on = [ aws_internet_gateway.this ]
}

# create NATGW
resource "aws_nat_gateway" "this" {
    allocation_id = aws_eip.this.id
    subnet_id     = aws_subnet.pub_sub1.id
    tags = {
        Name = "bombi-vpc-natgw"
    }
    lifecycle {
      create_before_destroy = true
    }
}

# create private subnet 
resource "aws_subnet" "pri_sub1" {
    vpc_id = aws_vpc.this.id #위에서 aws_vpc라는 리소스로 만든 this라는 리소스의 id
    cidr_block = "10.50.20.0/24"
    enable_resource_name_dns_a_record_on_launch = true
    availability_zone = "ap-northeast-2a"
    tags = {
        Name = "pri-sub1"
    }
    depends_on = [ aws_nat_gateway.this ]
}

resource "aws_subnet" "pri_sub2" {
    vpc_id = aws_vpc.this.id #위에서 aws_vpc라는 리소스로 만든 this라는 리소스의 id
    cidr_block = "10.50.21.0/24"
    enable_resource_name_dns_a_record_on_launch = true
    availability_zone = "ap-northeast-2c"
    tags = {
        Name = "pri-sub2"
    }
    depends_on = [ aws_nat_gateway.this ]
}

# 퍼블릭 라우팅 테이블 정의
resource "aws_route_table" "pub_rt" {
  vpc_id = aws_vpc.this.id

  route {
    cidr_block = "10.50.0.0/16"
    gateway_id = "local"
  }

  route {
    cidr_block = "0.0.0.0/0"
    gateway_id = aws_internet_gateway.this.id
  }
  tags = {
    Name = "bombi-vpc-pub-rt"
  }
}
# 프라이빗 라우팅 테이블 정의
resource "aws_route_table" "pri_rt" {
  vpc_id = aws_vpc.this.id

  route {
    cidr_block = "10.50.0.0/16"
    gateway_id = "local"
  }

  route {
    cidr_block = "0.0.0.0/0"
    gateway_id = aws_nat_gateway.this.id
  }
  tags = {
    Name = "bombi-vpc-pri-rt"
  }
}

# 퍼블릭 라우팅테이블과 퍼블릭 서브넷을 연결
resource "aws_route_table_association" "pub1_rt_asso" {
    subnet_id = aws_subnet.pub_sub1.id
    route_table_id = aws_route_table.pub_rt.id
}

resource "aws_route_table_association" "pub2_rt_asso" {
    subnet_id = aws_subnet.pub_sub2.id
    route_table_id = aws_route_table.pub_rt.id
}

# private routing table connection
resource "aws_route_table_association" "pri1_rt_asso" {
    subnet_id = aws_subnet.pri_sub1.id
    route_table_id = aws_route_table.pri_rt.id
}

resource "aws_route_table_association" "pri2_rt_asso" {
    subnet_id = aws_subnet.pri_sub2.id
    route_table_id = aws_route_table.pri_rt.id
}

# 퍼블릭 라우팅테이블과 퍼블릭 서브넷을 연결
resource "aws_route_table_association" "pub_rt_asso" {
    subnet_id = aws_subnet.pub_sub1.id
    route_table_id = aws_route_table.pub_rt.id
}

# create security group
resource "aws_security_group" "bombi-vpc-pub-sg" {
    vpc_id = aws_vpc.this.id
    name = "bombi-vpc-pub-sg"
    tags = {
        Name = "bombi-vpc-pub-sg"
    }
}

# http ingress
resource "aws_security_group_rule" "bombi-vpc-http-ingress" {
    type = "ingress"
    from_port = 80
    to_port = 80
    protocol = "TCP"
    cidr_blocks = ["0.0.0.0/0"]
    security_group_id = aws_security_group.bombi-vpc-pub-sg.id
    lifecycle {
      create_before_destroy = true
    }
  
}
# ssh ingress
resource "aws_security_group_rule" "bombi-vpc-ssh-ingress" {
    type = "ingress"
    from_port = 22
    to_port = 22
    protocol = "TCP"
    cidr_blocks = ["0.0.0.0/0"]
    security_group_id = aws_security_group.bombi-vpc-pub-sg.id
    lifecycle {
      create_before_destroy = true
    }
  
}

# egress
resource "aws_security_group_rule" "bombi-vpc-all-egress" {
    type = "egress"
    from_port = 0
    to_port = 0
    protocol = "-1"
    cidr_blocks = [ "0.0.0.0/0"]
    security_group_id = aws_security_group.bombi-vpc-pub-sg.id
    lifecycle {
      create_before_destroy = true
    }

}



# Private subnet security group
resource "aws_security_group" "bombi-vpc-pri-sg" {
    vpc_id = aws_vpc.this.id
    name = "bombi-vpc-pri-sg"
    
    # 내부 통신을 위한 인바운드 규칙을 여기서 직접 정의
    ingress {
        from_port = 0
        to_port = 0
        protocol = "-1"
        self = true  # 같은 보안 그룹 내 통신 허용
    }

    # Public 서브넷으로부터의 통신 허용
    ingress {
        from_port = 0
        to_port = 0
        protocol = "-1"
        security_groups = [aws_security_group.bombi-vpc-pub-sg.id]
    }

    # HTTPS 아웃바운드
    egress {
        from_port = 443
        to_port = 443
        protocol = "tcp"
        cidr_blocks = ["0.0.0.0/0"]
    }

    # HTTP 아웃바운드
    egress {
        from_port = 80
        to_port = 80
        protocol = "tcp"
        cidr_blocks = ["0.0.0.0/0"]
    }

    tags = {
        Name = "bombi-vpc-pri-sg"
    }

    lifecycle {
        create_before_destroy = true
    }
}
# resource "aws_security_group_rule" "allow_bigquery_api" {
#   type              = "egress"
#   from_port         = 443
#   to_port           = 443
#   protocol          = "tcp"
#   cidr_blocks       = ["*.googleapis.com"]  # BigQuery API endpoints
#   security_group_id = aws_security_group.bombi-vpc-pub-sg.id
# }

# resource "aws_iam_role" "bigquery_access" {
#   name = "bigquery-access-role"

#   assume_role_policy = jsonencode({
#     Version = "2012-10-17"
#     Statement = [
#       {
#         Action = "sts:AssumeRole"
#         Effect = "Allow"
#         Principal = {
#           Service = "ec2.amazonaws.com"
#         }
#       }
#     ]
#   })
# }

# resource "aws_iam_role_policy" "bigquery_access_policy" {
#   name = "bigquery-access-policy"
#   role = aws_iam_role.bigquery_access.id

#   policy = jsonencode({
#     Version = "2012-10-17"
#     Statement = [
#       {
#         Effect = "Allow"
#         Action = [
#           "bigquery:*"
#         ]
#         Resource = "*"
#       }
#     ]
#   })
# }

# IAM 역할 생성
# resource "aws_iam_role" "ssm_role" {
#     name = "ssm-role"
#     assume_role_policy = jsonencode({
#         Version = "2012-10-17"
#         Statement = [
#             {
#                 Action = "sts:AssumeRole"
#                 Effect = "Allow"
#                 Principal = {
#                     Service = "ec2.amazonaws.com"
#                 }
#             }
#         ]
#     })
# }

# # SSM 정책 연결
# resource "aws_iam_role_policy_attachment" "ssm_policy" {
#     role = aws_iam_role.ssm_role.name
#     policy_arn = "arn:aws:iam::aws:policy/AmazonSSMManagedInstanceCore"
# }

# # 인스턴스 프로필 생성
# resource "aws_iam_instance_profile" "ssm_profile" {
#     name = "ssm-profile"
#     role = aws_iam_role.ssm_role.name
# }

# # Private EC2 인스턴스에 프로필 적용
# resource "aws_instance" "private_instance" {
#     ami = "ami-0c9c942bd7bf113a2"
#     instance_type = "t2.micro"
#     subnet_id = aws_subnet.pri_sub1.id
#     vpc_security_group_ids = [aws_security_group.bombi-vpc-pri-sg.id]
#     iam_instance_profile = aws_iam_instance_profile.ssm_profile.name

#     tags = {
#         Name = "private-instance"
#     }
# }

# # VPC 엔드포인트 설정 (SSM 사용을 위해)
# resource "aws_vpc_endpoint" "ssm" {
#     vpc_id = aws_vpc.this.id
#     service_name = "com.amazonaws.ap-northeast-2.ssm"
#     vpc_endpoint_type = "Interface"
#     subnet_ids = [aws_subnet.pri_sub1.id]
#     security_group_ids = [aws_security_group.bombi-vpc-pri-sg.id]
#     private_dns_enabled = true
# }

# resource "aws_vpc_endpoint" "ssmmessages" {
#     vpc_id = aws_vpc.this.id
#     service_name = "com.amazonaws.ap-northeast-2.ssmmessages"
#     vpc_endpoint_type = "Interface"
#     subnet_ids = [aws_subnet.pri_sub1.id]
#     security_group_ids = [aws_security_group.bombi-vpc-pri-sg.id]
#     private_dns_enabled = true
# }

# resource "aws_vpc_endpoint" "ec2messages" {
#     vpc_id = aws_vpc.this.id
#     service_name = "com.amazonaws.ap-northeast-2.ec2messages"
#     vpc_endpoint_type = "Interface"
#     subnet_ids = [aws_subnet.pri_sub1.id]
#     security_group_ids = [aws_security_group.bombi-vpc-pri-sg.id]
#     private_dns_enabled = true
# }

# 쿠버네티스 API 서버
resource "aws_security_group_rule" "k8s_api" {
  type        = "ingress"
  from_port   = 6443
  to_port     = 6443
  protocol    = "tcp"
  cidr_blocks = ["0.0.0.0/0"]
  security_group_id = aws_security_group.bombi-vpc-pub-sg.id
}

# kubelet API
resource "aws_security_group_rule" "kubelet" {
  type        = "ingress"
  from_port   = 10250
  to_port     = 10250
  protocol    = "tcp"
  cidr_blocks = [aws_vpc.this.cidr_block]
  security_group_id = aws_security_group.bombi-vpc-pri-sg.id
}

# NodePort Services
resource "aws_security_group_rule" "nodeport" {
  type        = "ingress"
  from_port   = 30000
  to_port     = 32767
  protocol    = "tcp"
  cidr_blocks = ["0.0.0.0/0"]
  security_group_id = aws_security_group.bombi-vpc-pri-sg.id
}