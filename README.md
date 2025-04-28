# B2C Payment Service

A Spring Boot microservice for initiating Business-to-Consumer (B2C) mobile money payments in Kenya,
integrated with mobile money platforms (e.g., M-Pesa, Airtel Money) and an SMS gateway for notifications.

## Features

- RESTful API for initiating B2C payments and checking transaction status.
- Integration with mobile money platforms via abstractions.
- SMS notifications for transaction success/failure.
- OAuth2 authentication for secure API access.
- In-memory H2 database for transaction persistence.
- Comprehensive logging and error handling.
- Unit and integration tests covering edge cases.
- Dockerized for easy deployment.

## Prerequisites

- Java 17
- Maven
- Docker
- OAuth2 server (e.g., Auth0) for authentication

## Setup

1. Clone the repository:
   ```bash
   git clone https://github.com/mullatoes/b2cpaymentservice.git

2. Build the application
   ```With Maven (Locally):`
   ./mvnw clean install -DskipTests

3. Dockerization
   ``Build the Docker image:``

- docker build -t b2c-payment-service .
  ``Run the Docker container:``
- docker run -p 8080:8080 b2c-payment-service

4. Set up OAuth2 Server
   ``For authentication purposes, this app requires an OAuth2 provider like Auth0``
   ``spring:
     security:
       oauth2:
         resourceserver:
           jwt:
              issuer-uri: https://b6L3GYdhcSbr4Zceq8iKh2Ls4pFv4gDX-domain>/
   ``
5. Running the Application
   ``./mvnw spring-boot:run
  ``
6. Swagger UI
    - Once the application is running, you can access the Swagger UI to test the endpoints.

    - Swagger UI: http://localhost:8080/swagger-ui.html
    - API Documentation: http://localhost:8080/api-docs
7. Testing

- Unit and integration tests are included in the project to ensure proper functionality. Run the tests with:
  ``./mvnw test
 ``
- If you need to skip the tests:
  ``./mvnw clean install -DskipTests
  ``