data "local_file" "application_files" {
  for_each = fileset("${path.module}/application", "*.yaml")
  filename = "${path.module}/application/${each.value}"
}

resource "kubectl_manifest" "applications" {
  for_each  = data.local_file.application_files
  yaml_body = each.value.content

  depends_on = [
    module.eks
  ]
}

#resource "kubernetes_manifest" "application_manifests" {
#  for_each = data.local_file.application_files
#  manifest = yamldecode(each.value.content)
#  depends_on = [module.eks, data.local_file.application_files, module.eks_blueprints_addons]
#}