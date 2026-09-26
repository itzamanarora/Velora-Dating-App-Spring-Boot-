# Velora Dating App 💑

Velora is a modern Spring Boot-based dating application backend built with a modular service architecture. It provides a comprehensive platform for user management, authentication, profile handling, and matchmaking algorithms to create a scalable and engaging dating experience.

**Author:** Aman Arora  
**Role:** Software Developer

---

## 📋 Table of Contents

- [Tech Stack](#tech-stack)
- [Project Overview](#project-overview)
- [Architecture](#architecture)
- [Core Modules](#core-modules)
- [Main Features](#main-features)
- [Authentication & Security](#authentication--security)
- [Prerequisites](#prerequisites)
- [Environment Setup](#environment-setup)
- [Getting Started](#getting-started)
- [Database Migrations](#database-migrations)
- [API Documentation](#api-documentation)
- [Testing](#testing)
- [Project Structure](#project-structure)
- [Contributing](#contributing)
- [License](#license)

---

## 🛠 Tech Stack

| Component | Version | Purpose |
|-----------|---------|---------|
| Java | 21+ | Core language |
| Spring Boot | 4.0.8 | Framework |
| Spring Web MVC | - | REST API development |
| Spring Data JPA | - | Database abstraction |
| Spring Security | - | Authentication & authorization |
| JWT (JJWT) | 0.13.0 | Stateless authentication |
| PostgreSQL | - | Primary database |
| Flyway | - | Database migrations |
| Springdoc OpenAPI | 3.1.0 | API documentation (Swagger) |
| Lombok | - | Boilerplate reduction |
| Micrometer + Prometheus | - | Metrics & monitoring |
| Spring Mail | - | Email notifications |
| Thymeleaf | - | Email template rendering |
| Maven | - | Build tool |

---

## 📊 Project Overview

Velora is designed as a modular monolith with clear separation of concerns through distinct service modules. This architecture provides:

- **Scalability:** Service-oriented structure ready for microservices migration
- **Maintainability:** Clear module boundaries with dedicated responsibilities
- **Extensibility:** Easy to add new services (Profile Service, Matching Service, etc.)
- **Security:** Integrated Spring Security with JWT token-based authentication
- **Database Management:** Flyway migrations for schema versioning
- **Monitoring:** Built-in health checks and Prometheus metrics

### Key Workflow

1. **User Registration/Login** → JWT Token Generation
2. **Profile Management** → Data Persistence via JPA
3. **Authentication Layer** → Token Validation on Protected Endpoints
4. **Service Layer Processing** → Business Logic Execution
5. **Database Persistence** → PostgreSQL with Flyway Migrations
6. **Monitoring & Observability** → Actuator metrics exposed to Prometheus

---

## 🏗 Architecture

### Directory Structure

```text
Velora-Dating-App-Spring-Boot-/
├── src/main/java/com/aman/Velora/
│   ├── VeloraApplication.java          # Spring Boot entry point
│   ├── user_service/                   # User management module
│   │   ├── controller/                 # REST endpoints
│   │   ├── service/                    # Business logic
│   │   ├── repository/                 # JPA data access
│   │   ├── models/                     # Entity classes
│   │   ├── dto/                        # Data transfer objects
│   │   ├── mapper/                     # Entity-DTO mappers
│   │   ├── exception/                  # Custom exceptions
│   │   └── utils/                      # Utility functions
│   ├── auth_service/                   # Authentication & authorization
│   │   ├── controller/                 # Auth endpoints
│   │   ├── service/                    # Token & credential management
│   │   ├── config/                     # Security configuration
│   │   ├── filter/                     # JWT filter
│   │   ├── dto/                        # Auth DTOs
│   │   └── utils/                      # Auth utilities
│   └── common/                         # Shared utilities & configs
│       ├── exception/                  # Global exception handling
│       ├── config/                     # Application configuration
│       ├── dto/                        # Common DTOs
│       └── utils/                      # Shared utilities
├── src/main/resources/
│   ├── application.properties           # Application configuration
│   ├── application-{profile}.properties # Profile-specific configs
│   └── db/migration/                   # Flyway SQL migration files
├── src/test/java/                      # Unit & integration tests
├── pom.xml                             # Maven project file
├── mvnw & mvnw.cmd                     # Maven wrappers
├── .env                                # Environment variables
└── README.md                           # This file
```

### Service-Oriented Architecture

```
┌─────────────────────────────────────────────────────────┐
│                    Client Layer                          │
│              (Web/Mobile/Third-party APIs)              │
└──────────────────────┬──────────────────────────────────┘
                       │
        ┌──────────────┼──────────────┐
        ▼              ▼              ▼
   ┌─────────┐   ┌──────────┐   ┌─────────┐
   │ REST    │   │ REST     │   │ REST    │
   │Control. │   │Control.  │   │Control. │
   │(Auth)   │   │(User)    │   │(Match)  │
   └────┬────┘   └────┬─────┘   └────┬────┘
        │             │             │
   ┌────▼────────��────▼────────────▼──────┐
   │    Spring Security & JWT Filter      │
   └────┬──────────────────────────────────┘
        │
   ┌────▼──────────────────────────────────┐
   │     Service Layer (Business Logic)    │
   │  ├─ AuthService                       │
   │  ├─ UserService                       │
   │  ├─ ProfileService (future)           │
   │  └─ MatchService (future)             │
   └────┬──────────────────────────────────┘
        │
   ┌────▼──────────────────────────────────┐
   │  Repository Layer (Data Access)       │
   │  ├─ UserRepository (JPA)              │
   │  ├─ AuthTokenRepository (JPA)         │
   │  └─ ProfileRepository (JPA)           │
   └────┬──────────────────────────────────┘
        │
   ┌────▼──────────────────────────────────┐
   │      PostgreSQL Database              │
   │  (Managed by Flyway Migrations)       │
   └───────────────────────────────────────┘
        │
   ┌────▼──────────────────────────────────┐
   │   Monitoring & Observability          │
   │  ├─ Spring Actuator                   │
   │  └─ Prometheus Metrics                │
   └───────────────────────────────────────┘
```

---

## 📦 Core Modules

### 1. **User Service** (`user_service`)
Manages user account lifecycle, profile data, and user-related operations.

**Responsibilities:**
- User account creation & management
- Profile data handling (bio, photos, preferences)
- User search & filtering
- User deactivation/deletion

**Key Components:**
- `UserController` - REST endpoints
- `UserService` - Business logic
- `UserRepository` - JPA queries
- `UserEntity` - Database model

### 2. **Authentication Service** (`auth_service`)
Handles authentication, authorization, and JWT token lifecycle.

**Responsibilities:**
- User login & registration
- JWT token generation & validation
- Password hashing & verification
- Role-based access control (RBAC)

**Key Components:**
- `AuthController` - Auth endpoints
- `AuthService` - Token management
- `JwtFilter` - Token validation filter
- `SecurityConfig` - Spring Security configuration

### 3. **Common Module** (`common`)
Shared utilities, exception handling, and configuration.

**Responsibilities:**
- Global exception handling
- Common DTOs & response wrappers
- Application configuration
- Utility functions & helpers

---

## ✨ Main Features

### Authentication & Authorization
- ✅ JWT-based stateless authentication
- ✅ Spring Security integration
- ✅ Role-based access control (RBAC)
- ✅ Password encryption with BCrypt
- ✅ Token refresh mechanism

### User Management
- ✅ User registration & login
- ✅ Profile creation & management
- ✅ User preferences handling
- ✅ Account settings

### Database & Persistence
- ✅ JPA/Hibernate ORM
- ✅ PostgreSQL database
- ✅ Flyway database migrations
- ✅ Optimized queries & lazy loading

### API & Documentation
- ✅ RESTful API endpoints
- ✅ OpenAPI 3.0 specification
- ✅ Swagger UI auto-generated documentation
- ✅ Comprehensive error responses

### Monitoring & Health Checks
- ✅ Spring Actuator endpoints
- ✅ Prometheus metrics exposure
- ✅ Application health status
- ✅ System resource monitoring

### Future Features (Planned)
- 🔄 Real-time chat service
- 🔄 Advanced matchmaking algorithm
- 🔄 Photo upload & processing
- 🔄 Video call integration
- 🔄 Notification service (Push/SMS/Email)
- 🔄 Payment processing

---

## 🔐 Authentication & Security

Velora implements a secure, stateless authentication system using JWT tokens.

### Authentication Flow

```
1. User Registration
   └─> POST /api/auth/register
       └─> Validate credentials
           └─> Hash password
               └─> Store in database
                   └─> Return success message

2. User Login
   └─> POST /api/auth/login
       └─> Validate email & password
           └─> Generate JWT token
               └─> Return token to client

3. Protected Request
   └─> GET /api/users/profile
       └─> Header: Authorization: Bearer <jwt-token>
           └─> JwtFilter validates token
               └─> Spring Security grants access
                   └─> Execute business logic
                       └─> Return response
```

### JWT Token Structure

```http
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJhbWFuQGV4YW1wbGUuY29tIiwiaWF0IjoxNjk0NzAwMDAwfQ.signature
```

**Token Claims:**
- `sub` - Subject (User Email)
- `iat` - Issued At (Timestamp)
- `exp` - Expiration Time
- `roles` - User Roles
- `userId` - User ID

### Security Best Practices

- ✅ Passwords hashed with BCrypt
- ✅ JWT signed with secure key
- ✅ Token expiration configured
- ✅ CORS configured for API access
- ✅ SQL injection prevention via JPA
- ✅ XSS protection in responses

---

## 📋 Prerequisites

Before running the application, ensure you have:

- **Java 21** or higher - [Download Java](https://www.oracle.com/java/technologies/downloads/)
- **PostgreSQL 12** or higher - [Download PostgreSQL](https://www.postgresql.org/download/)
- **Maven 3.8** or higher - [Download Maven](https://maven.apache.org/download.cgi)
- **Git** - [Download Git](https://git-scm.com/downloads)
- **IDE** (Optional) - IntelliJ IDEA, Eclipse, or VS Code

### Verify Installation

```bash
# Check Java version
java -version

# Check Maven version
mvn -version

# Check PostgreSQL version
psql --version
```

---

## ⚙️ Environment Setup

### 1. Clone the Repository

```bash
git clone https://github.com/itzamanarora/Velora-Dating-App-Spring-Boot-.git
cd Velora-Dating-App-Spring-Boot-
```

### 2. Create PostgreSQL Database

```bash
# Connect to PostgreSQL
psql -U postgres

# Create database
CREATE DATABASE velora;

# Create user (optional)
CREATE USER velora_user WITH PASSWORD 'your_secure_password';

# Grant privileges
GRANT ALL PRIVILEGES ON DATABASE velora TO velora_user;

# Exit
\q
```

### 3. Configure Environment Variables

Create a `.env` file in the project root:

```env
# PostgreSQL Configuration
POSTGRES_HOST=localhost
POSTGRES_PORT=5432
POSTGRES_DB=velora
POSTGRES_USER=postgres
POSTGRES_PASSWORD=your_password

# Spring Boot Configuration
SPRING_PROFILE=dev
SERVER_PORT=8080

# JWT Configuration
JWT_SECRET=your_secret_key_minimum_32_characters_long
JWT_EXPIRATION=86400000

# Application Configuration
APP_NAME=Velora
APP_VERSION=0.0.1
```

### 4. Update application.properties

Edit `src/main/resources/application.properties`:

```properties
# Server Configuration
server.port=8080
server.servlet.context-path=/api

# PostgreSQL Database
spring.datasource.url=jdbc:postgresql://localhost:5432/velora
spring.datasource.username=postgres
spring.datasource.password=your_password
spring.datasource.driver-class-name=org.postgresql.Driver

# JPA/Hibernate
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=false
spring.jpa.properties.hibernate.format_sql=true

# Flyway Configuration
spring.flyway.baselineOnMigrate=true
spring.flyway.locations=classpath:db/migration

# JWT Configuration
app.jwt.secret=${JWT_SECRET:your_secret_key_minimum_32_characters_long}
app.jwt.expiration=${JWT_EXPIRATION:86400000}

# Actuator & Monitoring
management.endpoints.web.exposure.include=health,metrics,prometheus
management.metrics.export.prometheus.enabled=true

# OpenAPI/Swagger
springdoc.api-docs.path=/api-docs
springdoc.swagger-ui.path=/swagger-ui.html
springdoc.swagger-ui.enabled=true
```

---

## 🚀 Getting Started

### 1. Build the Application

```bash
# Using Maven wrapper (Linux/Mac)
./mvnw clean install

# Using Maven wrapper (Windows)
mvnw.cmd clean install

# Using system Maven
mvn clean install
```

### 2. Run the Application

```bash
# Using Maven wrapper (Linux/Mac)
./mvnw spring-boot:run

# Using Maven wrapper (Windows)
mvnw.cmd spring-boot:run

# Using IDE
# Right-click on VeloraApplication.java → Run
```

### 3. Verify Application Started

Application will start on:

```
http://localhost:8080
```

Check health status:

```bash
curl http://localhost:8080/api/actuator/health
```

Expected response:

```json
{
  "status": "UP"
}
```

### 4. Access API Documentation

Open your browser and navigate to:

```
http://localhost:8080/api/swagger-ui.html
```

This displays the interactive Swagger UI with all available API endpoints.

---

## 🗄 Database Migrations

Velora uses **Flyway** for database versioning and migrations.

### Migration Files Location

```
src/main/resources/db/migration/
```

### Creating a New Migration

1. Create a new SQL file following Flyway naming convention:

```sql
-- File: src/main/resources/db/migration/V1_2__Add_profile_table.sql
CREATE TABLE profiles (
    id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL UNIQUE,
    bio VARCHAR(500),
    age INTEGER,
    location VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);
```

2. Naming Convention: `V<version>__<description>.sql`
   - `V1` - Version number
   - `__` - Separator
   - `Add_profile_table` - Description (underscores represent spaces)
   - `.sql` - File extension

3. Apply migrations automatically when application starts

### View Migration History

```bash
# Check Flyway history in database
psql -U postgres -d velora

# Query flyway_schema_history table
SELECT * FROM flyway_schema_history;
```

---

## 📚 API Documentation

### Base URL

```
http://localhost:8080/api
```

### Authentication Endpoints

#### Register User
```http
POST /auth/register
Content-Type: application/json

{
  "email": "user@example.com",
  "password": "SecurePassword123!",
  "firstName": "John",
  "lastName": "Doe"
}

Response: 201 Created
{
  "id": 1,
  "email": "user@example.com",
  "firstName": "John",
  "lastName": "Doe",
  "createdAt": "2024-09-26T10:30:00Z"
}
```

#### Login User
```http
POST /auth/login
Content-Type: application/json

{
  "email": "user@example.com",
  "password": "SecurePassword123!"
}

Response: 200 OK
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "type": "Bearer",
  "expiresIn": 86400000
}
```

### User Endpoints

#### Get Current User Profile
```http
GET /users/me
Authorization: Bearer <token>

Response: 200 OK
{
  "id": 1,
  "email": "user@example.com",
  "firstName": "John",
  "lastName": "Doe",
  "createdAt": "2024-09-26T10:30:00Z"
}
```

#### Update User Profile
```http
PUT /users/{id}
Authorization: Bearer <token>
Content-Type: application/json

{
  "firstName": "Jane",
  "lastName": "Doe"
}

Response: 200 OK
```

### Monitoring Endpoints

#### Health Check
```http
GET /actuator/health
Response: 200 OK
{
  "status": "UP"
}
```

#### Prometheus Metrics
```http
GET /actuator/prometheus
Response: 200 OK
(Prometheus format metrics)
```

For complete API documentation, visit the Swagger UI at `http://localhost:8080/api/swagger-ui.html`

---

## 🧪 Testing

### Run All Tests

```bash
# Using Maven wrapper (Linux/Mac)
./mvnw test

# Using Maven wrapper (Windows)
mvnw.cmd test
```

### Run Specific Test Class

```bash
./mvnw test -Dtest=UserServiceTest
```

### Run Tests with Coverage

```bash
./mvnw test jacoco:report
```

### Test Files Location

```
src/test/java/com/aman/Velora/
```

### Test Structure

```java
@SpringBootTest
@AutoConfigureMockMvc
public class UserServiceTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private UserService userService;
    
    @Test
    public void testGetUser() throws Exception {
        // Test implementation
    }
}
```

---

## 📁 Project Structure Details

### Package Organization

```
com.aman.Velora
├── VeloraApplication.java
├── user_service/
│   ├── controller/
│   │   └── UserController.java
│   ├── service/
│   │   ├── UserService.java
│   │   └── UserServiceImpl.java
│   ├── repository/
│   │   └── UserRepository.java
│   ├── models/
│   │   └── UserEntity.java
│   ├── dto/
│   │   ├── CreateUserRequest.java
│   │   └── UserResponse.java
│   ├── mapper/
│   │   └── UserMapper.java
│   ├── exception/
│   │   └── UserNotFoundException.java
│   └── utils/
│       └── UserUtils.java
├── auth_service/
│   ├── controller/
│   │   └── AuthController.java
│   ├── service/
│   │   ├── AuthService.java
│   │   ├── JwtTokenProvider.java
│   │   └── AuthServiceImpl.java
│   ├── config/
│   │   └── SecurityConfig.java
│   ├── filter/
│   │   └── JwtAuthenticationFilter.java
│   ├── dto/
│   │   ├── LoginRequest.java
│   │   ├── RegisterRequest.java
│   │   └── AuthResponse.java
│   └── utils/
│       └── PasswordEncoder.java
└── common/
    ├── exception/
    │   ├── GlobalExceptionHandler.java
    │   └── AppException.java
    ├── config/
    │   └── ApplicationConfig.java
    ├── dto/
    │   └── ApiResponse.java
    └── utils/
        └── Constants.java
```

---

## 🤝 Contributing

Contributions are welcome! Please follow these guidelines:

### 1. Fork & Clone
```bash
git clone https://github.com/itzamanarora/Velora-Dating-App-Spring-Boot-.git
cd Velora-Dating-App-Spring-Boot-
```

### 2. Create Feature Branch
```bash
git checkout -b feature/your-feature-name
```

### 3. Make Changes & Commit
```bash
git add .
git commit -m "feat: add your feature description"
```

### 4. Push & Create Pull Request
```bash
git push origin feature/your-feature-name
```

### Commit Message Convention
- `feat:` - New feature
- `fix:` - Bug fix
- `docs:` - Documentation
- `style:` - Formatting
- `refactor:` - Code restructuring
- `test:` - Test updates
- `chore:` - Build/dependency updates

---

## 📝 License

This project is currently unlicensed. A license file will be added later. For more information, please contact the author.

---

## 📞 Contact & Support

**Author:** Aman Arora  
**Email:** [Your Email]  
**GitHub:** [@itzamanarora](https://github.com/itzamanarora)  
**LinkedIn:** [Your LinkedIn Profile]

### Support Resources

- 📖 [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- 📖 [Spring Security Guide](https://spring.io/guides/gs/securing-web/)
- 📖 [JWT Best Practices](https://tools.ietf.org/html/rfc7519)
- 📖 [PostgreSQL Documentation](https://www.postgresql.org/docs/)
- 📖 [Flyway Migrations](https://flywaydb.org/documentation/)

---

## 🗺 Roadmap

### v0.1.0 (Current)
- [x] User management & authentication
- [x] JWT-based security
- [x] Database migrations

### v0.2.0 (Upcoming)
- [ ] Profile service with photo uploads
- [ ] Advanced matchmaking algorithm
- [ ] Real-time chat service

### v0.3.0 (Future)
- [ ] Payment & subscription management
- [ ] Notification service (Email/Push)
- [ ] Video call integration
- [ ] Analytics & reporting

---

**Last Updated:** September 26, 2024  
**Version:** 0.0.1-SNAPSHOT

Happy coding! 🚀
