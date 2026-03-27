variable "aws_region" {
  default = "us-east-2"
}

variable "app_name" {
  default = "recipe-service"
}

variable "app_port" {
  default = 8080
}

variable "task_cpu" {
  default = "512"
}

variable "task_memory" {
  default = "1024"
}

variable "github_repo" {
  description = "GitHub org/repo used for OIDC trust policy, e.g. joecirillo/recipe-service"
}
