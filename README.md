# 🛒 Java E-Commerce Project

A full-featured e-commerce backend application built with **Spring Boot 3**, **Spring Security (JWT)**, **JPA (Hibernate)**, and **MySQL**.

---

## 📦 Features

- ✅ User Authentication with JWT
- ✅ Role-based Authorization
- ✅ Product & Category Management
- ✅ RESTful API Design
- ✅ Swagger API Documentation
- ✅ MySQL Integration using Spring Data JPA
- ✅ Auto-reload with Spring DevTools
- ✅ Secure Configuration with Profiles or Environment Variables  
  -- Pendinggggggggggggggggg

---

## 🔧 Tech Stack

| Layer         | Technology             |
| ------------- | ---------------------- |
| Backend       | Java 17, Spring Boot 3 |
| Security      | Spring Security, JWT   |
| Database      | MySQL, Spring Data JPA |
| Documentation | Swagger/OpenAPI        |
| Build Tool    | Maven                  |

---

## 🚀 Getting Started

### 1. Clone the project

```bash
git clone https://github.com/yourusername/java-ecommerce.git
cd java-ecommerce
```

### 2. Create database

Create a MySQL database named:

```sql
CREATE DATABASE e_commerce_java_proj;
```

### 3. Set environment variables (recommended)

You can use a `.env` file or system environment variables:

```env
DB_URL=jdbc:mysql://localhost:3306/e_commerce_java_proj
DB_USERNAME=root
DB_PASSWORD=yourpassword
JWT_SIGNER_KEY=your-256bit-secret
```

### 4. Run the application

```bash
./mvnw spring-boot:run
```

The app runs at:  
📍 `http://localhost:8080/e-commerce`

---

## 📚 API Documentation

After running the project, access Swagger UI at:  
👉 `http://localhost:8080/e-commerce/swagger-ui/index.html`

---

## 🛡️ Authentication

### Login endpoint:

```http
POST /auth/login
```

**Request Body:**

```json
{
  "email": "admin@ecommerce.com",
  "password": "admin"
}
```

**Response:**

```json
{
  "accessToken": "eyJhbGciOiJIUzI1NiIsInR...",
  "refreshToken": "eyJhbGciOiJIUzI1NiIsInR..."
}
```

Use the `accessToken` in headers:

```
Authorization: Bearer {accessToken}
```

---

## 📝 Environment Configuration

If using `application-secret.yml`, run with:

```bash
./mvnw spring-boot:run --spring.config.additional-location=file:./application-secret.yml
```

---

## ❗ .gitignore Recommendations

Make sure to ignore sensitive files:

```
/application-secret.yml
.env
```

---

## 📬 Contact

**Doan Duc Hai**  
📍 Hải Dương  
🌐 [Facebook](https://facebook.com) | [Instagram](https://instagram.com) | [Zalo](#)
