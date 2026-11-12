# Splitwise – Expense Sharing Microservices

A backend application inspired by Splitwise that helps users manage groups, share expenses, and track balances. Built using Java, Spring Boot, and a microservices architecture.

## Project Overview

The application is divided into independently developed Spring Boot services. Each service handles a specific business responsibility and communicates with other services when required.

## Architecture

```text
                  Client
                    |
                    v
               API Gateway
                    |
          -----------------------
          |          |          |
          v          v          v
      Auth       Group       Expense
     Service     Service      Service
          |          |          |
          v          v          v
       MySQL      MySQL       MySQL

             Discovery Server
                 (Eureka)
```

## Microservices

| Service | Responsibility |
|---|---|
| API Gateway | Entry point for client requests and JWT validation |
| Auth Service | User registration, login, and JWT generation |
| Discovery Server | Service registration and discovery using Eureka |
| Group Service | Group creation and membership management |
| Expense Service | Expense-related operations |

## Technology Stack

- **Language:** Java
- **Backend:** Spring Boot
- **Microservices:** Spring Cloud
- **Service Discovery:** Netflix Eureka
- **API Gateway:** Spring Cloud Gateway
- **Inter-service Communication:** OpenFeign
- **Resilience:** Resilience4j
- **Security:** Spring Security, JWT, RSA (RS256)
- **Database:** MySQL
- **Build Tool:** Maven
- **API Testing:** Postman
- **Version Control:** Git and GitHub

## Implemented Features

- User registration and authentication
- JWT-based authentication using RSA keys
- API Gateway security
- Service registration with Eureka
- Group creation
- Group membership management
- Add and remove group members
- Leave group functionality
- Inter-service communication using OpenFeign
- Circuit breaker and fallback handling

## Project Structure

```text
splitwise-microservices/
│
├── api-gateway/
├── authservice/
├── discovery-server/
├── expense-service/
├── group-service/
│
├── .gitignore
└── README.md
```

## Running the Application

1. Start the Discovery Server.
2. Start the Auth Service.
3. Start the Group Service.
4. Start the Expense Service.
5. Start the API Gateway.

Ensure that MySQL is running and each service has its own database configuration.

Check each service's `application.properties` for its configured port and database settings.

## API Testing

APIs can be tested using Postman.

For authenticated requests, provide the JWT token in the Authorization header:

```http
Authorization: Bearer <your-jwt-token>
```

## Future Improvements

- Complete expense management functionality
- Balance calculation and settlement
- Apache Kafka for event-driven communication
- Redis caching
- Docker and Docker Compose
- Distributed tracing and monitoring
- Unit and integration testing
- CI/CD pipeline

## Learning Goals

This project is being developed to understand practical microservices architecture, distributed communication, security, fault tolerance, and event-driven systems.

---

**Status:** Under Development