TestDB API Project
Overview

This project is a Spring Boot API that interacts with a MySQL database (testdb) and provides CRUD operations for a User table.
It also includes a simple JavaScript front-end UI to fetch and display user data, as well as Postman documentation for API testing.

The project stack:

Backend: Spring Boot 3/4 + Java 17 + Spring Data JPA
Database: MySQL 8 / MySQL Workbench
Frontend: HTML + JavaScript
API Testing: Postman
Prerequisites
Java 17
Ensure Java 17 is installed.

Check version:

java -version

++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

Maven
Required to build and run Spring Boot.

Check version:

mvn -version
MySQL 8
Installed and running.

++++++++++++++++++++++++++++++++++++++++++++++++++++++++++




Recommended: MySQL Workbench
Postman
Optional but recommended for API testing.
Browser
For running the JavaScript UI (index.html).
Database Setup
Open MySQL Workbench and connect to your local MySQL server.


USE COMMAND PROMPT: 
C:\Users\ABCD>

mysql -u root -p

root
++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

Create a database named testdb:

CREATE DATABASE testdb;
USE testdb;

Create a users table:

CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    address VARCHAR(255)
);

Insert some sample data:

INSERT INTO users (name, email, address) VALUES
('Alice', 'alice@example.com', '123 Main St'),
('Bob', 'bob@example.com', '456 Park Ave'),
('Charlie', 'charlie@example.com', '789 Broadway');



++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
Spring Boot Configuration

application.properties

spring.application.name=testdb_api

spring.datasource.url=jdbc:mysql://localhost:3306/testdb?useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=root
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect

server.port=8080

++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

Build and Run

mvn clean install
mvn spring-boot:run

++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

API will run at http://localhost:8080/
API Endpoints
Method	Endpoint	Description
GET	/users	Get all users
GET	/users/{id}	Get user by ID
GET	/users/{id}/name	Get user name by ID
GET	/users/{id}/email	Get user email by ID
GET	/users/{id}/address	Get user address by ID
POST	/users	Create new user
PUT	/users/{id}	Update existing user completely
PATCH	/users/{id}	Update existing user partially
DELETE	/users/{id}	Delete user by ID
GET	/users/select?ids=1,2,5	Get users by multiple IDs


++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

Front-End UI
index.html displays user data in a table.
Supports:
Buttons to load a specific number or range of users.
Input field to fetch users by specific IDs.
Clickable input fields remain highlighted when selected.
Open index.html in a browser to interact with the API.
Example JavaScript Fetch Code
function fetchUsers() {
    let ids = document.getElementById("userIds").value; // e.g., "1,2,7"
    fetch(`http://localhost:8080/users/select?ids=${ids}`)
        .then(res => res.json())
        .then(data => {
            let tbody = document.querySelector("#userTable tbody");
            tbody.innerHTML = "";
            data.forEach(user => {
                tbody.innerHTML += `<tr>
                    <td>${user.id}</td>
                    <td>${user.name}</td>
                    <td>${user.email}</td>
                    <td>${user.address || ''}</td>
                </tr>`;
            });
        });
}

++++++++++++++++++++++++++++++++++++++++++++++++++++++++++


Postman Documentation
Import the Postman collection JSON provided in Postman_Collection.json.
Test all endpoints without using the UI.
Supports dynamic query parameters (ids) for fetching multiple users.

++++++++++++++++++++++++++++++++++++++++++++++++++++++++++


How to Run
Start MySQL Workbench and ensure testdb is running.

Run the Spring Boot application:

mvn spring-boot:run

Open the UI in a browser:
Click buttons to load a certain number of users.
Enter user IDs in the input field to fetch specific users.


++++++++++++++++++++++++++++++++++++++++++++++++++++++++++


Test endpoints via Postman for GET, POST, PATCH, PUT, DELETE.


++++++++++++++++++++++++++++++++++++++++++++++++++++++++++


Notes
Spring Boot uses spring.jpa.hibernate.ddl-auto=update:
Automatically creates/updates tables if not exist.
Changes in entities will reflect in the database.
The UI fetches data using GET requests only.
Postman can be used for full CRUD testing.

This README covers everything step-by-step from setup to running your app.