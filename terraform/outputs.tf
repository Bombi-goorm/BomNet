# output "private_instance_1_ip" {
#   value = module.private_instance_1.private_ip
# }

# output "private_instance_2_ip" {
#   value = module.private_instance_2.private_ip
# }

# output "private_instance_3_ip" {
#   value = module.private_instance_3.private_ip
# }

# output "public_instance_ip" {
#   value = module.public_instance.public_ip
# }

output "bastion_public_ip" {
  value = module.bastion.public_ip
}

output "worker_private_ips" {
  value = [
    module.worker_node_1.private_ip,
    module.worker_node_2.private_ip,
    module.worker_node_3.private_ip
  ]
}