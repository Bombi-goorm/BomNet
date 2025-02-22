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





resource "aws_instance" "this" {
  ami           = var.ami_id
  instance_type = var.instance_type
  subnet_id     = var.subnet_id
  vpc_security_group_ids = var.security_group_ids
  key_name      = var.key_name
  
  root_block_device {
    volume_size = var.volume_size
    volume_type = "gp3"
  }

  tags = merge(
    {
      Name = var.instance_name
    },
    var.tags
  )
}

resource "aws_instance" "this" {
  # 기존 설정...
  user_data = var.user_data
}