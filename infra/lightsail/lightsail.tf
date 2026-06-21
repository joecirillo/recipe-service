resource "aws_lightsail_container_service" "recipe_service" {
  name        = var.app_name
  power       = "micro"
  scale       = 1
  is_disabled = false
}

resource "aws_lightsail_container_service_deployment_version" "recipe_service" {
  service_name = aws_lightsail_container_service.recipe_service.name

  container {
    container_name = var.app_name
    image          = "${aws_ecr_repository.recipe_service.repository_url}:latest"

    ports = {
      "8080" = "HTTP"
    }

    environment = {
      SPRING_DATASOURCE_URL                          = var.SPRING_DATASOURCE_URL
      SPRING_DATASOURCE_USERNAME                     = var.SPRING_DATASOURCE_USERNAME
      SPRING_DATASOURCE_PASSWORD                     = var.SPRING_DATASOURCE_PASSWORD
      SPRING_JPA_PROPERTIES_HIBERNATE_DEFAULT_SCHEMA = var.SPRING_JPA_PROPERTIES_HIBERNATE_DEFAULT_SCHEMA
      API_KEY                                        = var.API_KEY
      API_KEY_ADMIN                                  = var.API_KEY_ADMIN
    }
  }

  public_endpoint {
    container_name = var.app_name
    container_port = 8080

    health_check {
      path                = "/actuator/health"
      healthy_threshold   = 2
      unhealthy_threshold = 3
      interval_seconds    = 30
      timeout_seconds     = 5
      success_codes       = "200-299"
    }
  }
}
