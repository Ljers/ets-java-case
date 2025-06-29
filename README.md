# ETS Java Case

This is a file management API developed as part of a Java Spring Boot case study. It supports secure file upload, download, update, and deletion using JWT-based authentication. Swagger is integrated for API documentation and testing.

## 🔧 Tech Stack

- Java 17  
- Spring Boot 3  
- Spring Security (JWT Authentication)  
- Spring Data JPA (MySQL Database)  
- Swagger/OpenAPI 3  
- Maven

## 🚀 Features

- ✅ User registration and login with JWT token generation  
- ✅ Secure API endpoints with JWT authorization  
- ✅ Upload and store files (e.g., `.pdf`, `.docx`, `.png`)  
- ✅ Download and update existing files  
- ✅ Delete files by ID  
- ✅ Swagger UI integration for API testing

## 🛢️ Database

The project uses **MySQL** as the relational database.  
You can configure your connection in `application.properties`:

spring.datasource.url=jdbc:mysql://localhost:3306/ets
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update

> Make sure the database `ets` exists in MySQL before running the app.

## 📂 API Endpoints

### Authentication

- POST /api/auth/login – Authenticate and retrieve JWT token

### Users

- POST /api/users/create – Register a new user (**requires JWT**)

### Files

- POST /api/files/upload – Upload a file  
- GET /api/files – List all uploaded files  
- GET /api/files/{id}/download – Download a file  
- PUT /api/files/{id} – Update file by ID  
- DELETE /api/files/{id} – Delete file by ID

> All `/api/files/**` and `/api/users/create` endpoints require a valid JWT token

## 🔑 Swagger UI

Access the Swagger documentation and test the APIs:

http://localhost:8080/swagger-ui/index.html

Click Authorize and enter your token to access protected endpoints.
