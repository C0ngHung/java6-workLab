# 🌿 java6-workLab

> A **Spring Boot 3.5.x** lab project demonstrating **Spring Security**, **Thymeleaf MVC**, and **role-based authorization**.

---

## 👤 Author
- **ID:** PS43444  
- **Full Name:** Đào Công Hùng

---

## 🧰 Tech Stack

| Technology | Description |
|-------------|-------------|
| ☕ **Java 21** | Modern language features and performance |
| 🚀 **Spring Boot 3.5.7** | Application framework |
| 🌐 **Spring Web** | MVC and REST support |
| 🔐 **Spring Security** | Form login, remember-me, and authorization |
| 🗃️ **Spring Data JPA** | Prepared for persistence layer |
| 🧩 **Thymeleaf + Security Extras** | Dynamic HTML templates with security tags |
| 💡 **Lombok** | Boilerplate code reduction |
| 🏗️ **Maven** | Build and dependency management |

---

## ✨ Features

### 🔑 Authentication
- Custom **login page** with username & password parameters  
- Supports **Remember-Me** authentication with configurable cookie & key  

### 🛡️ Authorization
- **In-memory users** with roles:
  - `USER`
  - `ADMIN`
  - Both combined  
- Role-based access control via **`@PreAuthorize`** annotations  
- **Friendly Access Denied Page** for restricted routes  

### 🧭 MVC & Controller Flow
- Organized **Spring MVC structure** using Thymeleaf templates  
- Demonstration of **secured pages** and controller interactions  

---

## 📂 Project Structure (Key Parts)

