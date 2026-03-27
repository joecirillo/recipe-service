terraform {
  required_providers {
    aws = { source = "hashicorp/aws", version = "~> 5.0" }
  }

  backend "s3" {
    bucket         = "foodies-finds-tfstate"
    key            = "recipe-service/terraform.tfstate"
    region         = "us-east-2"
    dynamodb_table = "foodies-finds-tfstate-lock"
    encrypt        = true
  }
}

provider "aws" {
  region = var.aws_region
}
