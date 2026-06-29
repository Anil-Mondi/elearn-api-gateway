# API Gateway

## Overview

The **API Gateway** is the single entry point for the E-Learn Microservices Platform. It routes incoming client requests to the appropriate microservice, performs centralized authentication, enables service discovery through Eureka, and provides a secure and scalable access layer.

Instead of exposing individual microservices directly, all frontend requests pass through the API Gateway.

---

## Responsibilities

* Route requests to backend microservices
* Service Discovery using Eureka
* JWT Authentication
* Centralized CORS Configuration
* Request Routing
* Load Balancing
* API Security
* Cross-Origin Request Handling
* Health Monitoring using Spring Boot Actuator
* API Documentation Support

---

## Technology Stack

* Java 17
* Spring Boot 3
* Spring Cloud Gateway
* Spring Security
* Spring Cloud Netflix Eureka Client
* JWT
* Spring WebFlux
* Spring Boot Actuator
* Redis (Future)
* Prometheus Metrics

---

## Architecture

```text
Angular Frontend
        │
        ▼
   API Gateway
        │
        ▼
   Eureka Server
        │
 ┌──────┼─────────┬─────────┬──────────┐
 ▼      ▼         ▼         ▼          ▼
User  Course  Purchase  Review  Notification
```

---

## Current Features

* API Routing
* Eureka Client Registration
* JWT Token Validation
* Global CORS Configuration
* Health Check Endpoint
* Actuator Integration
* OpenAPI Support
* Centralized Request Entry Point

---

## Gateway Routes

| Route                   | Destination          |
| ----------------------- | -------------------- |
| `/api/users/**`         | User Service         |
| `/api/courses/**`       | Course Service       |
| `/api/purchases/**`     | Purchase Service     |
| `/api/reviews/**`       | Review Service       |
| `/api/notifications/**` | Notification Service |

---

## Service Discovery

The API Gateway automatically discovers backend services through Eureka.

Example:

```text
lb://USER-SERVICE
lb://COURSE-SERVICE
lb://PURCHASE-SERVICE
lb://REVIEW-SERVICE
```

No hardcoded service URLs are used.

---

## Security

The Gateway acts as the first layer of security.

Current implementation includes:

* JWT Authentication
* Public API Whitelisting
* Secure Route Protection
* Token Validation

---

## CORS

Global CORS configuration is managed directly by Spring Cloud Gateway.

Allowed Origins:

* http://localhost:4200
* https://elearn-ui.onrender.com

---

## Monitoring

Spring Boot Actuator endpoints are enabled.

Available endpoints:

* `/actuator/health`
* `/actuator/info`
* `/actuator/prometheus`
* `/actuator/metrics`

---

## Future Enhancements

* Redis Rate Limiting
* Request Logging Filter
* Response Logging Filter
* Correlation ID Filter
* Distributed Tracing (Zipkin)
* Circuit Breaker (Resilience4j)
* API Versioning
* Request Validation
* Global Exception Handling
* API Analytics
* Gateway Metrics Dashboard

---

## Project Structure

```text
api-gateway
│
├── config
│
├── security
│
├── filter
│
├── exception
│
├── util
│
├── resources
│
└── ApiGatewayApplication.java
```

---

## Role in E-Learn Platform

The API Gateway provides a centralized, secure, and scalable entry point for all frontend requests while hiding the internal microservice architecture. It simplifies client communication, improves security, and enables service discovery, making the platform easier to maintain and extend.

---

## Author

**Anil Mondi**

