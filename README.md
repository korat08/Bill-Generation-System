# 🧾 Bill Generation System

A Spring Boot + MySQL based backend system for managing billing, inventory, and order processing in an e-commerce setting. Includes authentication, real-time WhatsApp notifications, and daily sales reporting.

---

## 🚀 Features

### ✅ Authentication & Authorization
- JWT-based authentication system
- Role-based access: `ADMIN` and `USER`
- Secure endpoints with Spring Security

### 📦 Product & Category Management
- Create product categories
- Add, update, delete products
- Track product quantity and price
- Associate products with categories

### 🛒 Order & Billing System
- Users can place orders with multiple items
- Automatic GST and total bill calculation
- Generates a bill after successful order
- Stock is updated on payment success
- Rollback if payment fails (atomicity)

### 📉 Daily Sales Report
- Generates a CSV report with:
    - Product ID
    - Name
    - Price
    - Quantity sold today
    - Total sales amount

### 🔔 Notification System (Twilio WhatsApp)
- WhatsApp alerts using Twilio API
- Admin notified if product stock is low
- Customers receive order confirmation messages

---

## 🧰 Technology Stack

| Component      | Technology        |
|----------------|-------------------|
| Language       | Java              |
| Framework      | Spring Boot       |
| ORM            | JPA (Hibernate)   |
| Database       | MySQL             |
| Security       | Spring Security + JWT |
| Messaging      | Twilio WhatsApp API |
| Build Tool     | Maven             |
| File Export    | CSV (sales report) |

---

## 📂 Project Modules

### 1. Product Module
- Manage products and categories
- Handle stock levels

### 2. Order Module
- Process orders
- Calculate bills and apply GST
- Update stock based on orders

### 3. Inventory Module
- Low-stock threshold logic
- Admin alerts on low inventory

### 4. Notification Module
- Sends WhatsApp notifications
- Alerts admin and confirms customer payments

### 5. Auth Module
- Handles login/signup
- Issues JWT tokens
- Protects routes with Spring Security

---

## 📝 How it Works

1. Admin adds products and sets low-stock threshold.
2. User places an order → System calculates GST and total.
3. On payment success → bill generated, stock updated.
4. If stock < threshold → admin gets WhatsApp alert.
5. User gets confirmation on WhatsApp.
6. Daily report (CSV) includes today's sales summary.

---

## 🧑‍💻 Author

**Korat**  
Java Backend Developer | DSA | Spring Boot Enthusiast  
[GitHub Profile](https://github.com/korat08)

---
