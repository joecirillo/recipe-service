resource "aws_ecs_cluster" "main" {
  name = "foodies-finds"
}

resource "aws_cloudwatch_log_group" "recipe_service" {
  name = "/ecs/${var.app_name}"
}

resource "aws_ecs_task_definition" "recipe_service" {
  family                   = var.app_name
  requires_compatibilities = ["FARGATE"]
  network_mode             = "awsvpc"
  cpu                      = var.task_cpu
  memory                   = var.task_memory
  execution_role_arn       = aws_iam_role.task_execution.arn
  task_role_arn            = aws_iam_role.task.arn

  container_definitions = jsonencode([
    {
      name  = var.app_name
      image = "${aws_ecr_repository.recipe_service.repository_url}:latest"

      portMappings = [
        {
          containerPort = var.app_port
          protocol      = "tcp"
        }
      ]

      logConfiguration = {
        logDriver = "awslogs"
        options = {
          awslogs-group         = aws_cloudwatch_log_group.recipe_service.name
          awslogs-region        = var.aws_region
          awslogs-stream-prefix = "ecs"
        }
      }

      secrets = [
        {
          name      = "SPRING_DATASOURCE_URL"
          valueFrom = "arn:aws:ssm:${var.aws_region}:${data.aws_caller_identity.current.account_id}:parameter/foodies-finds/recipe-service/spring.datasource.url"
        },
        {
          name      = "SPRING_DATASOURCE_USERNAME"
          valueFrom = "arn:aws:ssm:${var.aws_region}:${data.aws_caller_identity.current.account_id}:parameter/foodies-finds/recipe-service/spring.datasource.username"
        },
        {
          name      = "SPRING_DATASOURCE_PASSWORD"
          valueFrom = "arn:aws:ssm:${var.aws_region}:${data.aws_caller_identity.current.account_id}:parameter/foodies-finds/recipe-service/spring.datasource.password"
        },
        {
          name      = "SPRING_JPA_PROPERTIES_HIBERNATE_DEFAULT_SCHEMA"
          valueFrom = "arn:aws:ssm:${var.aws_region}:${data.aws_caller_identity.current.account_id}:parameter/foodies-finds/recipe-service/spring.jpa.properties.hibernate.default_schema"
        }
      ]
    }
  ])
}

resource "aws_ecs_service" "recipe_service" {
  name            = var.app_name
  cluster         = aws_ecs_cluster.main.id
  task_definition = aws_ecs_task_definition.recipe_service.arn
  desired_count   = 1
  launch_type     = "FARGATE"

  network_configuration {
    subnets          = data.aws_subnets.public.ids
    security_groups  = [aws_security_group.ecs.id]
    assign_public_ip = true
  }

  load_balancer {
    target_group_arn = aws_lb_target_group.main.arn
    container_name   = var.app_name
    container_port   = var.app_port
  }

  depends_on = [aws_lb_listener.http]
}
