# 💇‍♂️ Salon Booking System - Spring Boot Application

This is a Spring Boot-based RESTful web application for managing salon appointments, customers, staff, services, and branches.

---

## 🚀 Features

- Manage customers, staff, services, and branches
- Book appointments with automatic end time calculation
- Many-to-Many support for services in an appointment
- Notifications for appointment updates
- Logger integration in service layer for traceability
- Full CRUD support for customers
- All entities and DTOs fully implemented
- Secure and scalable layered architecture

---

## 🛠️ Tech Stack

- **Java 17** / Java 8+
- **Spring Boot**
- **Spring Data JPA**
- **MySQL / PostgreSQL**
- **Lombok**
- **Hibernate Validator**
- **Maven / Gradle**

---

## 🏗️ Architecture

Client (Frontend)
|
v
Spring Boot REST API (Controller)
|
Service Layer (with Logging)
|
Repository (JPA)
|
Database (MySQL/PostgreSQL)



---

## 📁 Project Structure



src/
├── main/
│ ├── java/
│ │ └── com.sasika.salon.booking
│ │ ├── controller
│ │ ├── entity
│ │ ├── dto
│ │ ├── repository
│ │ ├── service
│ │ └── util
│ └── resources/
│ ├── application.properties
│ └── schema.sql / data.sql



---

## ⚙️ Getting Started

### 1. Clone the Repository

[//]: # (```bash)
git clone https://github.com/YOUR_USERNAME/salon-booking-system.git
cd salon-booking-system


### 2. Configure application.properties
spring.datasource.url=jdbc:mysql://localhost:3306/salon_booking_system
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update


### 3. Build and Run the App
# Using Maven
./mvnw spring-boot:run

# Or using Gradle
./gradlew bootRun

### 4. API Access
Once running, access the APIs at:
http://localhost:8080/api/

✅ Completed
Customer CRUD (with logging in service layer)

All Entities and DTOs implemented

Appointment entity with automatic end time calculation

Service-Appointment Many-to-Many relationship

Notification entity linked to appointments
---
🛣️ Next Steps
Staff and Service CRUD APIs

Email/SMS notifications

Swagger API documentation

Role-based access control

Unit and integration testing

---
📜 License
This project is open source under the MIT License.

---

👤 Author
Sasika Dilum
LinkedIn | Email

---

Let me know if you'd like me to export this as a `.md` file or commit it directly to your project.


