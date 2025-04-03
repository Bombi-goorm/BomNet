resource "kubernetes_horizontal_pod_autoscaler_v2" "auth-hpa" {
  metadata {
    name = "auth-hpa"
    namespace = "default"  # 또는 원하는 네임스페이스
  }

  spec {
    scale_target_ref {
      api_version = "apps/v1"
      kind        = "Deployment"
      name        = "auth-server-dep"  # 스케일링할 디플로이먼트 이름
    }

    min_replicas = 1
    max_replicas = 2

    metric {
      type = "Resource"
      resource {
        name = "cpu"
        target {
          type                = "Utilization"
          average_utilization = 60
        }
      }
    }

    # 메모리 기반 스케일링을 원하는 경우
    metric {
      type = "Resource"
      resource {
        name = "memory"
        target {
          type                = "Utilization"
          average_utilization = 60
        }
      }
    }

    behavior {
      scale_up {
        select_policy = "Max"
        stabilization_window_seconds = 300  # 60초 동안 CPU 사용률 유지 시 확장

        # ✅ "policy" 블록이 최소 1개 이상 있어야 함!
        policy {
          type           = "Percent"
          value          = 50  # 기존보다 50% 증가
          period_seconds = 30
        }
      }

      scale_down {
        select_policy = "Min"
        stabilization_window_seconds = 300  # 120초 동안 CPU 사용률 유지 시 축소

        # ✅ "policy" 블록이 최소 1개 이상 있어야 함!
        policy {
          type           = "Percent"
          value          = 40  # 기존보다 20% 감소
          period_seconds = 60
        }
      }
    }
  }
}

resource "kubernetes_horizontal_pod_autoscaler_v2" "core-hpa" {
  metadata {
    name = "core-hpa"
    namespace = "default"  # 또는 원하는 네임스페이스
  }

  spec {
    scale_target_ref {
      api_version = "apps/v1"
      kind        = "Deployment"
      name        = "core-server-dep"  # 스케일링할 디플로이먼트 이름
    }

    min_replicas = 1
    max_replicas = 5

    metric {
      type = "Resource"
      resource {
        name = "cpu"
        target {
          type                = "Utilization"
          average_utilization = 60
        }
      }
    }

    # 메모리 기반 스케일링을 원하는 경우
    metric {
      type = "Resource"
      resource {
        name = "memory"
        target {
          type                = "Utilization"
          average_utilization = 60
        }
      }
    }
  }
}

resource "kubernetes_horizontal_pod_autoscaler_v2" "llm-hpa" {
  metadata {
    name = "llm-hpa"
    namespace = "default"  # 또는 원하는 네임스페이스
  }

  spec {
    scale_target_ref {
      api_version = "apps/v1"
      kind        = "Deployment"
      name        = "llm-server-dep"  # 스케일링할 디플로이먼트 이름
    }

    min_replicas = 1
    max_replicas = 5

    metric {
      type = "Resource"
      resource {
        name = "cpu"
        target {
          type                = "Utilization"
          average_utilization = 60
        }
      }
    }

    # 메모리 기반 스케일링을 원하는 경우
    metric {
      type = "Resource"
      resource {
        name = "memory"
        target {
          type                = "Utilization"
          average_utilization = 60
        }
      }
    }
  }
}

resource "kubernetes_horizontal_pod_autoscaler_v2" "notification-hpa" {
  metadata {
    name = "notification-hpa"
    namespace = "default"  # 또는 원하는 네임스페이스
  }

  spec {
    scale_target_ref {
      api_version = "apps/v1"
      kind        = "Deployment"
      name        = "notification-server-dep"  # 스케일링할 디플로이먼트 이름
    }

    min_replicas = 1
    max_replicas = 5

    metric {
      type = "Resource"
      resource {
        name = "cpu"
        target {
          type                = "Utilization"
          average_utilization = 60
        }
      }
    }

    # 메모리 기반 스케일링을 원하는 경우
    metric {
      type = "Resource"
      resource {
        name = "memory"
        target {
          type                = "Utilization"
          average_utilization = 60
        }
      }
    }
  }
}