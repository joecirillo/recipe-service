# Foodies Finds

## Overview
Foodies Find is a backend service for the Foodies Finds application, which helps users discover and share their favorite food spots. This repository contains the server-side code, including APIs that call the database.

## Tech Stack
- Java 21
- Spring Boot 3.3.4
- Maven
- PostgreSQL
- Docker
- JPA/Hibernate

## Local Setup (MacOS)
### Prerequisites

We recommend using Homebrew to manage package installations on MacOS. We will use Brew to
install most requirements.

- Homebrew (https://brew.sh/)
- Java 21 (https://formulae.brew.sh/formula/openjdk@21)
- Maven (https://formulae.brew.sh/formula/maven)
- PostgreSQL (https://formulae.brew.sh/formula/postgresql@14)
- Docker (https://www.docker.com/products/docker-desktop)

### Setup Steps
1. Clone the Repository
2. Run PostgreSQL:
   ```bash
   brew services start postgresql@14
   ```
3. Create Database
4. Update `application.properties` with your database credentials.
5. Build the Project:
   ```bash
      mvn clean install
      ```
6. Run the Application:
   ```bash
      mvn spring-boot:run
      ```