resource "aws_s3_bucket" "recipe_images" {
  bucket = var.S3_BUCKET_NAME
}

resource "aws_s3_bucket_public_access_block" "recipe_images" {
  bucket = aws_s3_bucket.recipe_images.id

  block_public_acls       = false
  block_public_policy     = false
  ignore_public_acls      = false
  restrict_public_buckets = false
}

resource "aws_s3_bucket_policy" "recipe_images_public_read" {
  bucket = aws_s3_bucket.recipe_images.id

  depends_on = [aws_s3_bucket_public_access_block.recipe_images]

  policy = jsonencode({
    Version = "2012-10-17"
    Statement = [
      {
        Sid       = "PublicReadGetObject"
        Effect    = "Allow"
        Principal = "*"
        Action    = "s3:GetObject"
        Resource  = "${aws_s3_bucket.recipe_images.arn}/*"
      }
    ]
  })
}

resource "aws_s3_bucket_cors_configuration" "recipe_images" {
  bucket = aws_s3_bucket.recipe_images.id

  cors_rule {
    allowed_methods = ["GET"]
    allowed_origins = ["*"]
    allowed_headers = ["*"]
  }
}
