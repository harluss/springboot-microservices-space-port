# Space Port

Microservices Architecture Assignment 2023

## Description

This is part 1 of the assignment - single microservice that communicates with database container to store information
about Spaceships.

*UPDATE:*
Second microservice was added for part 2 - similarly to the first one, it communicates with its own database container
to store information about Pilots.

## Table of Contents

* [Features](#features)
  * [Hangar microservice](#hangar-microservice)
    * [API](#api)
    * [Sample spaceship JSON](#sample-spaceship-response)
  * [Cantina microservice](#cantina-microservice)
    * [API](#api-1)
    * [Sample pilot JSON](#sample-pilot-response)
  * [Port microservice](#port-microservice)
    * [API](#api-2)
    * [Sample spaceship JSON](#sample-spaceship-response)
  * [Common](#common)
    * [Sample error JSON](#sample-error)
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

### Hangar microservice

#### API

```
GET     /api/v1/spaceships
GET     /api/v1/spaceships/{id}
POST    /api/v1/spaceships
PUT     /api/v1/spaceships/{id}
DELETE  /api/v1/spaceships/{id}
```

#### Sample spaceship response

```json
 {
  "id": "9e075e25-3bd8-466e-b978-cf38a07ff85b",
  "name": "Millennium Falcon",
  "maxSpeed": 1200,
  "payload": 100,
  "crewIds": [
    "8ed6e335-56cb-4512-b1fb-5a55faa1057c",
    "e1d4e41b-c72e-4fb7-b3bd-6b86e96b20f1"
  ],
  "armament": [
    "Quad laser cannons",
    "Concussion missile tubes"
  ],
  "class": "Light freighter"
}
```

### Cantina microservice

#### API

```
GET     /api/v1/pilots
GET     /api/v1/pilots/{id}
POST    /api/v1/pilots
PUT     /api/v1/pilots/{id}
DELETE  /api/v1/pilots/{id}
```

#### Sample pilot response

```json
    {
  "id": "58ce4ada-ecac-454d-abfe-1a7ed8ae9ccf",
  "name": "Greedo",
  "species": "Rodian",
  "profession": "Bounty hunter",
  "weapons": [
    "Blaster pistol"
  ]
}
```

### Port microservice

#### API

```
GET     /api/v1/spaceships
```

#### Sample spaceship response

TBC

### Common

#### Sample error JSON:

```json
{
  "status": 400,
  "error": "BAD_REQUEST",
  "message": "Validation error. Check 'errors' field for details",
  "errors": [
    {
      "field": "crew",
      "message": "must be greater than or equal to 0"
    },
    {
      "field": "name",
      "message": "must not be blank"
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

### APIs and Swagger

Spaceship APIs are exposed at:

```
http://localhost:8080/api/v1/spaceships
```

Swagger UI is available at:

```
http://localhost:8080
```

Pilot APIs are exposed at:

```
http://localhost:8081/api/v1/pilots
```

Swagger UI is available at:

```
http://localhost:8081
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
