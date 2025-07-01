# 🛒 Java E-Commerce Project

A full-featured e-commerce backend application built with **Spring Boot 3**, **Spring Security (JWT)**, **JPA (Hibernate)**, and **MySQL**.

---

📦 Features
✅ User Authentication with JWT
Đăng ký, đăng nhập, xác thực người dùng bằng JSON Web Token (JWT).

✅ Role-Based Authorization
Quản lý phân quyền bằng roles, permissions, role_permissions, user_roles.

✅ Product & Category Management
CRUD sản phẩm, phân loại theo nhiều danh mục với bảng product_categories.

✅ Product Variants & Inventory
Quản lý biến thể sản phẩm: size, màu sắc, giá, số lượng với bảng product_variants.

✅ Shopping Cart System
Thêm, cập nhật, xóa sản phẩm trong giỏ hàng (carts, cart_items).

✅ Order Management
Đặt hàng, lưu thông tin đơn hàng (orders, order_items).

✅ Review & Rating System
Người dùng đánh giá và bình luận sản phẩm (reviews), liên kết với đơn hàng.

✅ Wishlist Functionality
Lưu sản phẩm yêu thích bằng bảng wishlists.

✅ Coupon & Discount System
Mã giảm giá có giới hạn sử dụng, điều kiện áp dụng (coupons, discount_code_user_usage).

✅ Payments & Transactions
Quản lý thanh toán qua bảng payments liên kết với đơn hàng.

✅ Address Management
Người dùng có thể thêm nhiều địa chỉ giao hàng (addresses).

✅ RESTful API Design
Thiết kế chuẩn REST với các route rõ ràng, dễ mở rộng.

✅ Swagger API Documentation
Tự động tạo tài liệu API với Swagger UI.

✅ MySQL Integration with Spring Data JPA
Sử dụng Spring Data JPA kết nối và thao tác với cơ sở dữ liệu MySQL.

✅ Auto-reload with Spring DevTools
Tự động reload khi thay đổi code trong môi trường phát triển.

✅ Secure Configuration with Profiles/Environment Variables
Cấu hình bảo mật thông qua file application-{profile}.yml hoặc biến môi trường.


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
