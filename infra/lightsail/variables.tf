variable "aws_region" {
  default = "us-west-2"
}

variable "app_name" {
  default = "recipe-service"
}

variable "github_repo" {
  description = "GitHub org/repo used for OIDC trust policy, e.g. joecirillo/recipe-service"
}

variable "SPRING_DATASOURCE_URL" {
  sensitive = true
}

variable "SPRING_DATASOURCE_USERNAME" {
  sensitive = true
}

variable "SPRING_DATASOURCE_PASSWORD" {
  sensitive = true
}

variable "SPRING_JPA_PROPERTIES_HIBERNATE_DEFAULT_SCHEMA" {
  sensitive = true
}

variable "API_KEY" {
  sensitive = true
}

variable "API_KEY_ADMIN" {
  sensitive = true
}
