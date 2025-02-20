# vpc/outputs.tf

output "vpc_id" {
  value = aws_vpc.this.id
}

output "private_subnet_1_id" {
  value = aws_subnet.pri_sub1.id
}

output "private_subnet_2_id" {
  value = aws_subnet.pri_sub2.id
}

output "public_subnet_1_id" {
  value = aws_subnet.pub_sub1.id
}

output "private_security_group_id" {
  value = aws_security_group.bombi-vpc-pri-sg.id
}

output "public_security_group_id" {
  value = aws_security_group.bombi-vpc-pub-sg.id
}