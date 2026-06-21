resource "aws_ecr_repository" "recipe_service" {
  name         = "foodies-finds/recipe-service"
  force_delete = true
}
