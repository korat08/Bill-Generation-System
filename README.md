🧾 Bill Generation System
A complete Spring Boot + MySQL backend application to automate e-commerce billing and inventory management. Includes GST calculation, secure authentication, real-time stock tracking, role-based access, and multi-channel notifications (WhatsApp, SMS, Email).

🚀 Features
🛒 Product and Category Management (Admin)
🧾 GST-Inclusive Bill Generation
🔐 JWT-Based Authentication & Role-Based Authorization
📉 Inventory Management:
Stock Auto-Update on Order
Low Stock Threshold Alerts to Admin
🔄 Payment Verification with Rollback Support
📤 Multi-Channel Notifications:
✅ WhatsApp (via Twilio)
✅ SMS (via Twilio)
✅ Email (via JavaMailSender)
📈 Daily Sales CSV Report Generation

🛠 Tech Stack
Layer	Technology
Backend	Spring Boot, Spring Web
Auth	Spring Security, JWT
Database	MySQL (Spring Data JPA)
Email	JavaMailSender
SMS/Chat	Twilio (SMS & WhatsApp)
Reporting	OpenCSV
Build Tool	Maven