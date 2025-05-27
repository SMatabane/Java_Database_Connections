# 🎓 Student Management System – JDBC Project

Welcome to the **Student Management System** built using **Java JDBC and MySQL**.
This project is designed to help beginners understand how to connect Java applications to a database using **JDBC (Java Database Connectivity)**.

> ⚠️ _This project is still in progress. Stay tuned for more features and improvements!_ 🚧

---

## 💡 What You’ll Learn

- Java JDBC API
- MySQL database operations (CRUD)
- Database connection pooling (optional)
- Exception handling and logging
- Clean DAO (Data Access Object) design pattern
- UUID-based primary keys
- Basic use of Prepared Statements

---

## 🧰 Technologies Used

| Technology | Description                       |
|------------|-----------------------------------|
| Java       | Core programming language         |
| JDBC       | Java Database Connectivity        |
| MySQL      | Relational database               |
| Maven      | Build and dependency management   |
| Log4j/SLF4J| Logging framework                 |
| UUID       | Universal unique student IDs      |

---

## 📁 Features

- ✅ Add a new student
- 📋 View all students
- 🔍 Get student by ID
- ✏️ Update student details
- ❌ Delete a student
- 🆔 Use UUID for unique student IDs
- 🛡️ Graceful SQL exception handling

---

## 📦 Database Schema

```sql
CREATE TABLE students (
    studentID VARCHAR(36) PRIMARY KEY,
    name VARCHAR(100),
    email VARCHAR(100),
    course VARCHAR(50),
    marks INT
);
