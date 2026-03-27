output "alb_dns_name" {
  description = "Public URL of the load balancer"
  value       = "http://${aws_lb.main.dns_name}"
}

output "ecr_repository_url" {
  description = "ECR repository URL for docker push"
  value       = aws_ecr_repository.recipe_service.repository_url
}

output "github_actions_role_arn" {
  description = "ARN of the GitHub Actions deploy role — set as AWS_DEPLOY_ROLE_ARN in GitHub secrets"
  value       = aws_iam_role.github_actions_deploy.arn
}
