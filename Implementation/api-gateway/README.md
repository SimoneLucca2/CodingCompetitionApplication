# Spring Boot Application

## Overview
This Spring Boot application serves as an API gateway, utilizing Eureka for service discovery and including configurations for JWT authentication and JPA repositories.

## Prerequisites
- JDK 11 or later
- Maven or Gradle (depending on your project setup)
- MySQL Database (or any other SQL database compatible with Hibernate)

## Setup Instructions

### 1. Clone the Repository

### 2. Configure Database
Ensure that your MySQL database is running.

Update the `src/main/resources/application.yaml` file with your database connection details.
### 3. Add Dependencies
Verify that your `pom.xml` file contains the necessary dependencies for Spring Boot, Spring Data JPA, Spring MVC, and MySQL.
### 4. Build the Project
Use the Maven command to build the project. Here's how you can do it:
`mvn clean install`

