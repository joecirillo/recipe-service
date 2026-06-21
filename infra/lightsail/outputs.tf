output "lightsail_url" {
  description = "Public URL of the Lightsail container service"
  value       = "https://${aws_lightsail_container_service.recipe_service.url}"
}

output "ecr_repository_url" {
  description = "ECR repository URL for docker push"
  value       = aws_ecr_repository.recipe_service.repository_url
}

output "github_actions_role_arn" {
  description = "ARN of the GitHub Actions deploy role — set as AWS_DEPLOY_ROLE_ARN in GitHub secrets"
  value       = aws_iam_role.github_actions_deploy.arn
}
