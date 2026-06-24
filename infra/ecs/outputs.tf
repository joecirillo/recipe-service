output "ecr_repository_url" {
  description = "ECR repository URL for docker push"
  value       = aws_ecr_repository.recipe_service.repository_url
}

output "github_actions_role_arn" {
  description = "ARN of the GitHub Actions deploy role — set as AWS_DEPLOY_ROLE_ARN in GitHub secrets"
  value       = aws_iam_role.github_actions_deploy.arn
}

output "recipe_images_bucket_name" {
  description = "Name of the S3 bucket used for recipe images"
  value       = aws_s3_bucket.recipe_images.bucket
}
