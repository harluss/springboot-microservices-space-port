# Space Port

Microservices Architecture Assignment 2023

## Table of Contents

* [Features](#features)
  * [API Gateway](#api-gateway)
  * [Eureka Service Discovery](#eureka-service-discovery)
  * [Zipkin Tracing](#zipkin-tracing)
  * [Microservices API](#microservices-api)
* [Setup](#setup)
  * [Run locally](#run-locally)
  * [Tests](#tests)
  * [Requirements](#requirements)

## Requirements

- [x] Min 2 separate Microservices
- [x] CRUD operations
- [x] API documentation with Swagger
- [x] Service Discovery with Eureka Service
- [x] API Gateway with Spring Cloud Gateway
- [x] Tracing with Sleuth and Zipkin
- [x] Config with Spring Cloud Config
- [x] Database with Spring Data JPA
- [x] Data validation with Spring Bean Validation
- [x] Authentication with Spring Boot Security and JWT

### API Gateway

Login endpoint exposed at:

```
http://localhost:8080/api/auth/token
```

Spaceship APIs exposed at:

```
http://localhost:8080/api/v1/spaceships
```

### Eureka Service Discovery

Console exposed at:

```
http://localhost:8761
```

### Zipkin Tracing

Console exposed at:

```
http://localhost:9411/zipkin
```

### Microservices API

Port APIs exposed at:

```
http://localhost:<random_port>/api/v1/spaceships
```

Spaceship APIs exposed at:

```
http://localhost:<random_port>/api/v1/spaceships
```

Pilot APIs exposed at:

```
http://localhost:<random_port>/api/v1/pilots
```

Swagger UIs available at:

```
http://localhost:<random_port>/api/docs
```

## Setup

### Run locally

Start 2 Postgres containers, one for each service (it will create required table and seed dummy data):

```bash
docker compose up -d
```

Start the application in your IDE.

Login details for demo user to access API through API Gateway:

```json
{
  "username": "Keef",
  "password": "password"
}
```

### Tests

Run unit tests:

```bash
mvn test
```

Run unit and integration tests:

```bash
mvn clean verify
```

### Requirements

- Java 17
- Docker
- Maven
