# Spring Student Portal

A full stack student and course management portal built with Spring Boot, Spring Security, JWT authentication, and React.

## Tech Stack

**Backend**
- Java 17
- Spring Boot
- Spring Security
- JWT (JSON Web Tokens)
- PostgreSQL
- Spring Data JPA / Hibernate

**Frontend**
- React (Vite)
- Vanilla CSS with CSS Variables

---

## Features

- Student login with JWT authentication
- Role based access control (ROLE_USER, ROLE_ADMIN)
- Student can view and enroll in courses
- Admin can add new courses
- Admin can view students enrolled in each course
- Stateless authentication (no sessions)
- BCrypt password encoding
- CORS configured for React frontend

---

## Project Structure

```
spring-student-portal/
    src/
        main/java/com/example/learnSpringSecurity/
            Entity/
                Student.java
                Course.java
            Repository/
                StudentRepo.java
                CourseRepo.java
            Security/
                StudentDetails.java
                StudentDetailsService.java
            Service/
                AuthService.java
                JwtService.java
                Implementation/
                    AuthServiceImpl.java
            Controller/
                AuthController.java
                StudentController.java
                AdminController.java
            Config/
                SecurityConfig.java
                DataInitializer.java
                JwtFilter.java
            DTO/
                AuthResponse.java
                Login.java
    frontend/
        src/
            App.jsx
        package.json
    pom.xml
```

---

## Getting Started

### Prerequisites
- Java 17+
- Maven
- PostgreSQL
- Node.js
- Git

---

### 1. Clone the repository

```bash
git clone https://github.com/YourUsername/spring-student-portal.git
cd spring-student-portal
```

---

### 2. Configure database

Create a PostgreSQL database:

```sql
CREATE DATABASE student_portal;
```

Update `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/student_portal
spring.datasource.username=your_db_username
spring.datasource.password=your_db_password
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
jwt.secretKey=your_secret_key_minimum_32_characters_long
```

---

### 3. Run Spring Boot

```bash
./mvnw spring-boot:run
# Runs on http://localhost:8080
```

---

### 4. Run React frontend

```bash
cd frontend
npm install
npm run dev
# Runs on http://localhost:5173
```

---

### 5. Open in browser

```
http://localhost:5173
```

---

## API Endpoints

### Public
| Method | URL | Description |
|--------|-----|-------------|
| POST | `/auth/login` | Login and get JWT token |

### Student (ROLE_USER)
| Method | URL | Description |
|--------|-----|-------------|
| GET | `/courses` | Get all courses |
| GET | `/students/me/courses` | Get my enrolled courses |
| POST | `/students/me/enroll/{id}` | Enroll in a course |

### Admin (ROLE_ADMIN)
| Method | URL | Description |
|--------|-----|-------------|
| POST | `/admin/courses` | Add new course |
| GET | `/admin/courses` | Get all courses |
| GET | `/admin/courses/{id}/students` | Get students in course |
| GET | `/admin/students` | Get all students |

---

## Default Users (created on startup)

| Role | Email | Password |
|------|-------|----------|
| Student | Deepansh@gmail.com | password |
| Admin | admin@portal.com | admin123 |

---

## How JWT Authentication Works

```
1. User sends POST /auth/login with credentials
2. Spring Security verifies via DaoAuthenticationProvider
3. Server generates JWT token with username and role
4. Client stores token in localStorage
5. Client sends token in Authorization header on every request
6. JwtFilter validates token and sets authentication in SecurityContext
7. Controller reads user from SecurityContext
```

---

## Security

- Passwords are encoded using BCrypt
- JWT tokens expire after 24 hours
- Role based access using @PreAuthorize
- CORS configured for localhost:5173
- Stateless sessions (no server side sessions)

