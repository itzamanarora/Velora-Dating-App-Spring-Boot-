# Velora

Velora is a Spring Boot-based dating application backend built as a monolithic service. It provides the core foundation for user management, roles, persistence, and API exposure using Java and PostgreSQL.

Author: Aman Arora  
Role: Software Developer

## Tech Stack

- Java 21
- Spring Boot 4.0.8
- Spring Web MVC
- Spring Data JPA
- Spring Security
- JWT (JSON Web Token) authentication
- PostgreSQL
- Flyway
- Springdoc OpenAPI
- Maven
- Micrometer + Prometheus

## Project Overview

This project is structured as a monolith, which means all backend logic is organized within a single application. The current codebase focuses on user and role management, database migrations, and API services, with authentication and authorization being handled using Spring Security and JWT.

## Architecture

```text
backend/
├── src/main/java/com/aman/Velora/
│   ├── user/
│   │   ├── controller/
│   │   ├── service/
│   │   ├── repository/
│   │   ├── models/
│   │   ├── dto/
│   │   ├── mapper/
│   │   ├── exception/
│   │   └── utils/
│   └── VeloraApplication.java
├── src/main/resources/
│   ├── application.properties
│   └── db/migration/
├── src/test/java/
├── pom.xml
├── mvnw
├── mvnw.cmd
├── .env
└── README.md
```

## Main Features

- User and role entity models
- JPA repositories for persistence
- Service layer for business logic
- REST controllers for API endpoints
- Spring Security integration
- JWT-based authentication and authorization
- Flyway database migration support
- OpenAPI/Swagger UI integration
- Actuator health and monitoring endpoints

## Authentication & Security

This application will use Spring Security for authentication and authorization, with JWT tokens for stateless API security.

Typical flow:

- User registers or logs in
- Server validates credentials
- JWT token is generated and returned to the client
- Client sends the token in the Authorization header
- Spring Security validates the token for protected endpoints

Example header:

```http
Authorization: Bearer <jwt-token>
```

## Prerequisites

- Java 21+
- PostgreSQL
- Maven
- Git

## Environment Setup

Create a `.env` file in the project root:

```env
POSTGRES_HOST=localhost
POSTGRES_PORT=5432
POSTGRES_DB=velora
POSTGRES_USER=postgres
POSTGRES_PASSWORD=your_password
```

## Run the Application

```bash
./mvnw spring-boot:run
```

For Windows:

```bash
mvnw.cmd spring-boot:run
```

Application will start on:

```text
http://localhost:8080
```

## Run Tests

```bash
./mvnw test
```

## Database Migration

The project uses Flyway to manage database schema changes. Migration files are stored under:

```text
src/main/resources/db/migration/
```

## Notes

- This repository currently follows a monolithic backend design.
- It is ready for expansion into separate services later if the project grows.
- Spring Security is integrated with JWT-based authentication for secure access control.
- The app is structured for future extension and additional role-based authorization.

## License

This project is currently unlicensed unless a license file is added later.

## Contact

Aman Arora  
Software Developer
