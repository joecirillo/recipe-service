# Foodies Finds

## Overview
Foodies Find is a backend service for the Foodies Finds application, which helps users discover and share their favorite food spots. This repository contains the server-side code, including APIs that call the database.

## Tech Stack
- Java 21
- Spring Boot 3.3.4
- Maven
- PostgreSQL
- JPA/Hibernate
- AWS Parameter Store (configuration)

## Local Setup (MacOS)
### Prerequisites

We recommend using Homebrew to manage package installations on MacOS.

- Homebrew (https://brew.sh/)
- Java 21 (https://formulae.brew.sh/formula/openjdk@21)
- Maven (https://formulae.brew.sh/formula/maven)
- AWS CLI v2 (https://formulae.brew.sh/cask/awscli)
- AWS account access with permissions to SSM Parameter Store

### Setup Steps
1. Clone the repository.
2. Log in to AWS:
   ```bash
   aws login
   ```
3. Export your SSO session credentials into the current shell:
   ```bash
   eval "$(aws configure export-credentials --format env)"
   ```
   This reads your active SSO token and sets `AWS_ACCESS_KEY_ID`, `AWS_SECRET_ACCESS_KEY`, and `AWS_SESSION_TOKEN` as environment variables. These are picked up automatically by the app at startup to authenticate with AWS Parameter Store. **You will need to re-run this command whenever your session expires.**
4. Build the project:
   ```bash
   mvn clean install
   ```
5. Run the application:
   ```bash
   mvn spring-boot:run
   ```

### Configuration
Database credentials and connection details are stored in AWS Parameter Store under `/foodies-finds/recipe-service/`. No local configuration is required — the app pulls all settings from AWS on startup.
