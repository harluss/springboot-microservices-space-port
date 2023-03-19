# Space Port

Microservices Architecture Assignment 2023

## Todo

- [ ] description
- [ ] requirements
- [ ] Add rest of CRUD to Space Port
- [ ] Add GitHub Action job to run `mvn clean verify` on commit and PR
- [ ] Add auth with JWT

## Table of Contents

* [Features](#features)
  * [API Gateway](#api-gateway)
  * [Eureka Service Discovery](#eureka-service-discovery)
  * [Zipkin Tracing](#zipkin-tracing)
  * [Port microservice](#port-microservice)
    * [API](#api-2)
    * [Spaceship JSON schema](#spaceship-json-schema)
  * [Hangar microservice](#hangar-microservice)
    * [API](#api)
    * [Spaceship JSON schema](#spaceship-json-schema)
  * [Cantina microservice](#cantina-microservice)
    * [API](#api-1)
    * [Pilot JSON schema](#pilot-json-schema)
  * [Common](#common)
    * [Error JSON schema](#error-json-schema)
* [Setup](#setup)
  * [Run locally](#run-locally)
  * [Tests](#tests)
  * [Requirements](#requirements)

## Features

- Postgres database
- CRUD operations
- Request data validation
- Global exception handling
- Custom mapper for DTOs
- Unit tests with Junit5 and REST-assured
- Integration tests with Testcontainers and WireMock
- Swagger documentation
- Service Discovery with Eureka Service
- API Gateway
- Tracing with Sleuth and Zipkin
- Cloud Config

### API Gateway

APIs exposed at:

```
http://localhost:8080/api/v1/spaceships
```

Swagger UI exposed at:

```
http://localhost:8080/api/docs
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

### Port microservice

#### API

Port APIs exposed at:

```
http://localhost:<random_port>/api/v1/spaceships
```

Swagger UI available at:

```
http://localhost:<random_port>/api/docs
```

Available endpoints:

```
GET     /api/v1/spaceships
GET     /api/v1/spaceships/{id}
POST    /api/v1/spaceships
```

#### Spaceship JSON schema

```json
[
  {
    "id": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
    "name": "string",
    "maxSpeed": 0,
    "payload": 0,
    "crew": [
      {
        "id": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
        "name": "string",
        "species": "string",
        "profession": "string",
        "weapons": [
          "string"
        ]
      }
    ],
    "armament": [
      "string"
    ],
    "class": "string"
  }
]
```

### Hangar microservice

#### API

Spaceship APIs exposed at:

```
http://localhost:<random_port>/api/v1/spaceships
```

Swagger UI available at:

```
http://localhost:<random_port>/api/docs
```

Available endpoints:

```
GET     /api/v1/spaceships
GET     /api/v1/spaceships/{id}
POST    /api/v1/spaceships
PUT     /api/v1/spaceships/{id}
DELETE  /api/v1/spaceships/{id}
```

#### Spaceship JSON schema

```json
 {
  "id": "9e075e25-3bd8-466e-b978-cf38a07ff85b",
  "name": "string",
  "maxSpeed": 0,
  "payload": 0,
  "crewIds": [
    "8ed6e335-56cb-4512-b1fb-5a55faa1057c",
    "e1d4e41b-c72e-4fb7-b3bd-6b86e96b20f1"
  ],
  "armament": [
    "string"
  ],
  "class": "string"
}
```

### Cantina microservice

#### API

Pilot APIs exposed at:

```
http://localhost:<random_port>/api/v1/pilots
```

Swagger UI available at:

```
http://localhost:<random_port>/api/docs
```

Available endpoints:

```
GET     /api/v1/pilots
GET     /api/v1/pilots/{id}
POST    /api/v1/pilots
PUT     /api/v1/pilots/{id}
DELETE  /api/v1/pilots/{id}
```

#### Pilot JSON schema

```json
{
  "id": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
  "name": "string",
  "species": "string",
  "profession": "string",
  "weapons": [
    "string"
  ]
}
```

### Common

#### Error JSON schema

```json
{
  "status": 0,
  "error": "string",
  "message": "string",
  "errors": [
    {
      "field": "string",
      "message": "string"
    }
  ]
}
```

## Setup

### Run locally

Start 2 Postgres containers, one for each service (it will create required table and seed dummy data):

```bash
docker compose up -d
```

Start the application in your IDE.

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
