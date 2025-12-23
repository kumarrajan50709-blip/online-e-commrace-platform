# Online-E-Commerace-Platform
**🛒 Online E-Commerce Platform (Java GUI)
📌 Project Overview**

This project is a Java GUI-based Online E-Commerce Platform developed using Java Swing and JDBC.
It demonstrates core Java concepts, database connectivity, transaction management, and multithreading to simulate real-world order processing.

The application ensures atomic order execution using JDBC commit() and rollback(), maintaining data integrity across inventory and order operations.

**🚀 Key Features**
✅ Core Functionalities

Product listing

Cart management

Order placement

Inventory stock update

Multi-user order simulation

**🔄 Transaction Management (JDBC)**

Uses setAutoCommit(false)

commit() on successful order

rollback() on failure

Ensures atomic and consistent order processing

**🧵 Multithreading**

Multiple users place orders simultaneously

Implemented using Runnable and Thread

Simulates concurrent order processing

**🛡 Error Handling & Validation**

SQL exception handling

Stock validation before order placement

Rollback on any failure to prevent data corruption

**🖥 Java Swing GUI**

User-friendly interface

Event-driven programming

Efficient event handling and delegation

**🧩 Project Structure**
src/
 └── com.ecommerce
     ├── ui
     │   └── MainFrame.java
     ├── dao
     │   └── ProductDAO.java
     ├── db
     │   └── DBConnection.java
     ├── model
     │   ├── Product.java
     │   └── CartItem.java
     └── thread
         └── OrderTask.java

**🗄 Database Details**

Database: MySQL

Tables Used:

products

orders

Stock is updated only if the transaction is successful.

**🧪 Testing**

Tested for:

Concurrent user orders

Insufficient stock scenarios

Database failure rollback

Application runs without crashes and handles failures gracefully.

**💡 Innovation / Extra Effort**

Multithreaded order simulation

Proper JDBC transaction handling

Clean modular architecture

Real-world concurrency handling

**🛠 Technologies Used**

Java

Java Swing

JDBC

MySQL

IntelliJ IDEA
