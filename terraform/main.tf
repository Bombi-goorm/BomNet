# VPC 모듈 호출
module "vpc" {
  source = "./vpc"
}

# locals {
#   master_user_data = <<-EOF
# #!/bin/bash

# # 로그 파일 설정
# exec > >(tee /var/log/user-data.log|logger -t user-data -s 2>/dev/console) 2>&1

# echo "Starting installation..."

# # 시스템 업데이트
# apt-get update
# apt-get upgrade -y

# # 호스트명 설정
# hostnamectl set-hostname master-node

# # swap 비활성화
# swapoff -a
# sed -i '/ swap / s/^\(.*\)$/#\1/g' /etc/fstab

# # 필수 패키지 설치
# apt-get install -y apt-transport-https ca-certificates curl software-properties-common

# # 도커 설치
# curl -fsSL https://download.docker.com/linux/ubuntu/gpg | gpg --dearmor -o /usr/share/keyrings/docker-archive-keyring.gpg
# echo "deb [arch=$(dpkg --print-architecture) signed-by=/usr/share/keyrings/docker-archive-keyring.gpg] https://download.docker.com/linux/ubuntu $(lsb_release -cs) stable" | tee /etc/apt/sources.list.d/docker.list > /dev/null
# apt-get update
# apt-get install -y docker-ce docker-ce-cli containerd.io

# # 도커 서비스 시작
# systemctl start docker
# systemctl enable docker

# # 쿠버네티스 저장소 추가
# curl -fsSLo /usr/share/keyrings/kubernetes-archive-keyring.gpg https://packages.cloud.google.com/apt/doc/apt-key.gpg
# echo "deb [signed-by=/usr/share/keyrings/kubernetes-archive-keyring.gpg] https://apt.kubernetes.io/ kubernetes-xenial main" | tee /etc/apt/sources.list.d/kubernetes.list

# # 쿠버네티스 컴포넌트 설치
# apt-get update
# apt-get install -y kubelet kubeadm kubectl
# apt-mark hold kubelet kubeadm kubectl

# # 도커 데몬 설정
# cat > /etc/docker/daemon.json <<EOL
# {
#   "exec-opts": ["native.cgroupdriver=systemd"],
#   "log-driver": "json-file",
#   "log-opts": {
#     "max-size": "100m"
#   },
#   "storage-driver": "overlay2"
# }
# EOL

# # 도커 재시작
# systemctl daemon-reload
# systemctl restart docker

# # 필요한 모듈 활성화
# modprobe br_netfilter
# echo "net.bridge.bridge-nf-call-iptables = 1" | tee -a /etc/sysctl.conf
# sysctl -p

# # 쿠버네티스 초기화
# kubeadm init --pod-network-cidr=10.244.0.0/16 --ignore-preflight-errors=NumCPU

# # kubeconfig 설정
# mkdir -p /root/.kube
# cp -i /etc/kubernetes/admin.conf /root/.kube/config
# chown $(id -u):$(id -g) /root/.kube/config

# # ubuntu 사용자를 위한 kubeconfig 설정
# mkdir -p /home/ubuntu/.kube
# cp -i /etc/kubernetes/admin.conf /home/ubuntu/.kube/config
# chown ubuntu:ubuntu /home/ubuntu/.kube/config

# # Flannel CNI 설치
# kubectl apply -f https://raw.githubusercontent.com/flannel-io/flannel/master/Documentation/kube-flannel.yml

# # 설치 완료 표시
# echo "Installation completed" > /root/installation_complete

# EOF

#   worker_user_data = <<-EOF
# #!/bin/bash -xe

# # 시스템 업데이트
# apt-get update && apt-get upgrade -y

# # 호스트명 설정
# hostnamectl set-hostname worker-node

# # swap 비활성화
# swapoff -a
# sed -i '/ swap / s/^\(.*\)$/#\1/g' /etc/fstab

# # 필수 패키지 설치
# apt-get install -y apt-transport-https ca-certificates curl software-properties-common

# # 컨테이너 런타임(Docker) 설치
# curl -fsSL https://download.docker.com/linux/ubuntu/gpg | apt-key add -
# add-apt-repository "deb [arch=amd64] https://download.docker.com/linux/ubuntu $(lsb_release -cs) stable"
# apt-get update
# apt-get install -y docker-ce docker-ce-cli containerd.io

# # 도커 데몬 설정
# cat > /etc/docker/daemon.json <<EOL
# {
#   "exec-opts": ["native.cgroupdriver=systemd"],
#   "log-driver": "json-file",
#   "log-opts": {
#     "max-size": "100m"
#   },
#   "storage-driver": "overlay2"
# }
# EOL

# mkdir -p /etc/systemd/system/docker.service.d
# systemctl daemon-reload
# systemctl restart docker
# systemctl enable docker

# # 쿠버네티스 저장소 추가
# curl -s https://packages.cloud.google.com/apt/doc/apt-key.gpg | apt-key add -
# echo "deb https://apt.kubernetes.io/ kubernetes-xenial main" > /etc/apt/sources.list.d/kubernetes.list
# apt-get update

# # 쿠버네티스 컴포넌트 설치
# apt-get install -y kubelet kubeadm kubectl
# apt-mark hold kubelet kubeadm kubectl

# EOF
# }

# Public 서브넷의 마스터 노드
module "bastion" {
  source = "./modules/ec2"

  ami_id             = "ami-04cebc8d6c4f297a3"  # Ubuntu 22.04 LTS
  instance_type      = "t3.medium"
  subnet_id          = module.vpc.public_subnet_1_id
  security_group_ids = [module.vpc.public_security_group_id]
  key_name           = var.key_name
  instance_name      = "bastion"
  volume_size        = 30
  
  tags = {
    Environment = "production"
    Type        = "bastion"
  }
}

# Private 서브넷의 워커 노드들
module "worker_node_1" {
  source = "./modules/ec2"

  ami_id             = "ami-04cebc8d6c4f297a3"  # Ubuntu 22.04 LTS
  instance_type      = "t3.medium"
  subnet_id          = module.vpc.private_subnet_1_id
  security_group_ids = [module.vpc.private_security_group_id]
  key_name           = var.key_name
  instance_name      = "k8s-worker-1"
  volume_size        = 30
  
  tags = {
    Environment = "production"
    Type        = "worker"
  }
}

module "worker_node_2" {
  source = "./modules/ec2"

  ami_id             = "ami-04cebc8d6c4f297a3"  # Ubuntu 22.04 LTS
  instance_type      = "t3.medium"
  subnet_id          = module.vpc.private_subnet_1_id
  security_group_ids = [module.vpc.private_security_group_id]
  key_name           = var.key_name
  instance_name      = "k8s-worker-2"
  volume_size        = 30
  
  tags = {
    Environment = "production"
    Type        = "worker"
  }
}

module "worker_node_3" {
  source = "./modules/ec2"

  ami_id             = "ami-04cebc8d6c4f297a3"  # Ubuntu 22.04 LTS
  instance_type      = "t3.medium"
  subnet_id          = module.vpc.private_subnet_2_id
  security_group_ids = [module.vpc.private_security_group_id]
  key_name           = var.key_name
  instance_name      = "k8s-worker-3"
  volume_size        = 30
  
  tags = {
    Environment = "production"
    Type        = "worker"
  }
}