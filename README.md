# Code Kata Battle

Code competition application.

This web application is built using Spring Boot (backend) and React (frontend), leveraging microservices architecture for scalability and resilience. 
It utilizes Kafka for messaging and Eureka for service discovery to facilitate communication and management among the microservices.
For more details about the requirements: https://github.com/SimoneLucca2/FeraboliFilippiniLucca/blob/main/DeliveryFolder/RASD-v1.0.pdf
(exclude the part of handling badges)

## Prerequisites

Before you begin, ensure you have met the following requirements:
- JDK 11 or newer
- Node.js and npm
- Docker and Docker Compose (optional for containerization)

## Architecture Overview

![image](https://github.com/SimoneLucca2/FeraboliFilippiniLucca/assets/106387524/77db9cc5-d7e0-49cf-aa4d-acab7fa40504)

The communication between client and server is RESTful, using json serialized messages through HTTP.

For more details about the architecture design: https://github.com/SimoneLucca2/FeraboliFilippiniLucca/blob/main/DeliveryFolder/DD-v1.0.pdf

## Setup and Installation

### Backend using docker compose:
1. Ensure to have docker installed in your system and the docker daemon running.
2. Open the terminal and go in the /implementation directory of the ckb project.
3. Run docker-compose up sonarqube
4. Open the browser
5. Go to “http://localhost:9000”
6. Access SonarQube – Default user name and password are “admin”
7. Click on the user logo on the right side of the screen
8. Go on security
9. Generate a new token – Choose as token type “user-token”
10. Copy the token an put it in the docker-compose.yaml as the value of “SONAR_TOKEN”
11. Now go to “github.com”
12. Login into your account
13. Generate a new token
14. Copy the token in the docker-compose.yaml file as the value of “GITHUB_API_TOKEN”
15. Set the value of “GITHUB_API_USERNAME” your github name
16. Run docker-compose up – This will start the backend API with all the services associated to it.

### Web server
1.	Install node.js.
2.	Use the terminal to move to the /implementation/ckbfronted directory.
3.	Run the command “npm install react-scripts”.
4.	Run “npm start” – this will start the web server on port 3000.
To use the application just use a browser to connect to <web server ip>:3000.
