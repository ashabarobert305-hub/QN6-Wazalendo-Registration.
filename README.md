# QN6-Wazalendo-Registration.

A robust, lightweight Java Swing desktop application designed for the front-desk clerk at **Wazalendo SACCO** to register new members efficiently while maintaining absolute data integrity. The application validates member records in real-time and securely commits them to a Microsoft Access database.

---

## 🚀 Features

*   **User-Friendly GUI:** Form layout engineered in Java Swing featuring rapid input fields and quick action keys (`Register`, `Clear`, `Exit`).
*   **Proactive Data Integrity Rules:** Front-end validations protect the system against corrupt or incomplete records before sending data down the pipeline.
*   **Secure JDBC Integration:** Utilizes `PreparedStatement` parameters to interface with MS Access, entirely eliminating risks associated with SQL Injection.
*   **Automatic Resource Cleanup:** Implements modern `try-with-resources` blocks to prevent database connection leaks.

---

## 🛡️ Validation & Data Integrity Controls

To protect database integrity, the program executes these checks upon clicking **Register**:
1.  **Presence Check:** Disallows empty strings across all fields.
2.  **Length Constraints:** Verifies the National ID (NIN) is exactly 14 characters long.
3.  **Format Constraints:** Evaluates the phone number format using regex rules to require exactly 10 numerical digits.
4.  **Value-Range Constraints:** Parses the initial deposit value to guarantee it is both a valid number and strictly greater than `0 UGX`.

---

## 📊 Database Schema Architecture

The backend table relies on the following schema configured inside Microsoft Access:

```sql
CREATE TABLE Members (
    MemberID VARCHAR(50) PRIMARY KEY,
    FullName VARCHAR(100) NOT NULL,
    NIN VARCHAR(14) NOT NULL,
    PhoneNumber VARCHAR(10) NOT NULL,
    InitialDeposit DOUBLE NOT NULL
);


🛠️ Setup and Installation
Prerequisites
Java Development Kit (JDK): Version 8 or higher.

Microsoft Access Database: A .accdb file containing the Members table.

UCanAccess Drivers: You need the UCanAccess driver suite jar files (ucanaccess-x.x.x.jar, jackcess-x.x.x.jar, commons-lang3-x.x.jar, commons-logging-x.x.jar, hsqldb-x.x.jar) added to your project classpath.

Configuration Steps
1.   Clone the Repository:

Bash
git clone [https://github.com/YOUR_USERNAME/QN6-Wazalendo-Registration.git](https://github.com/YOUR_USERNAME/QN6-Wazalendo-Registration.git)
cd QN6-Wazalendo-Registration

2.   Database Connection String:
Open WazalendoRegistration.java and modify the static database file path string to match your computer's local system pathway layout:

Java
private static final String DB_URL = "jdbc:ucanaccess://C:/YourFolder/WazalendoDB.accdb";

3.    Compile and Run:
Using your preferred IDE (IntelliJ IDEA, Eclipse, NetBeans) or command line:

Bash
javac WazalendoRegistration.java
java WazalendoRegistration















