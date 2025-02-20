variable "key_name" {
  description = "SSH key pair name"
  type        = string
}

variable "instance_type" {
  description = "EC2 instance type"
  type        = string
  default     = "t2.micro"
}

variable "ami_id" {
  description = "AMI ID for instances"
  type        = string
  default     = "ami-0c9c942bd7bf113a2"  # Amazon Linux 2 AMI
}