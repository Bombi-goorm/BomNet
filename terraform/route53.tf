# ALB 정보 조회
data "aws_lb" "alb" {
  name = "eks-alb"  # ALB의 실제 이름
  depends_on = [ module.eks ]
}

# Route 53 호스팅 영역 조회
data "aws_route53_zone" "existing" {
  name = "bomnet.shop"
}
locals {
  subdomains = ["llm", "auth", "core", "notification"]
}
# 여러 A 레코드 생성

resource "aws_route53_record" "alb_records" {
  zone_id = data.aws_route53_zone.existing.zone_id
  name    = "bomnet.shop"  # subdomain 없이 도메인 이름만 사용
  type    = "A"

  alias {
    name                   = data.aws_lb.alb.dns_name
    zone_id                = data.aws_lb.alb.zone_id
    evaluate_target_health = true
  }

  lifecycle {
    create_before_destroy = true
  }
}

resource "aws_route53_record" "alb_records_sub" {
  for_each = toset(local.subdomains)

  zone_id = data.aws_route53_zone.existing.zone_id
  name    = "${each.value}.${data.aws_route53_zone.existing.name}"
  type    = "A"

  alias {
    name                   = data.aws_lb.alb.dns_name
    zone_id                = data.aws_lb.alb.zone_id
    evaluate_target_health = true
  }

  lifecycle {
    create_before_destroy = true
  }
}
