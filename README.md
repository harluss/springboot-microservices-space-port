# Space Port

Microservices Architecture Assignment 2023

This is part 1 of the assignment - single microservice that communicates with database container to store information
about Spaceships.

## Table of Contents

* [Features](#features)
  * [Hangar microservice](#hangar-microservice)
    * [API](#api)
    * [Spaceship JSON schema](#spaceship-json-schema)
  * [Cantina microservice](#cantina-microservice)
    * [API](#api-1)
    * [Pilot JSON schema](#pilot-json-schema)
  * [Port microservice](#port-microservice)
    * [API](#api-2)
    * [Spaceship JSON schema](#spaceship-json-schema)
  * [Common](#common)
    * [Error JSON schema](#error-json-schema)
* [Setup](#setup)
  * [Run locally](#run-locally)
  * [APIs and Swagger](#apis-and-swagger)
  * [Tests](#tests)
  * [Requirements](#requirements)

## Features

- Postgres database
- CRUD operations
- Request data validation
- Global exception handling
- Custom mapper for DTOs
- Unit tests with Junit5 and REST-assured
- Integration tests with Testcontainers
- Swagger documentation
- Service Discovery with Eureka Service

### Hangar microservice

#### API

Spaceship APIs are exposed at:

```
http://localhost:8080/api/v1/spaceships
```

Swagger UI is available at:

```
http://localhost:8080
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

Pilot APIs are exposed at:

```
http://localhost:8081/api/v1/pilots
```

Swagger UI is available at:

```
http://localhost:8081
```

Available endpoints:

```
GET     /api/v1/pilots
GET     /api/v1/pilots/{id}
POST    /api/v1/pilots
POST    /api/v1/pilots/crew
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

### Port microservice

#### API

Pilot APIs are exposed at:

```
http://localhost:8080/api/v1/spaceships
```

Swagger UI is available at:

```
http://localhost:8080
```

Available endpoints:

```
GET     /api/v1/spaceships
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
