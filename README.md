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
  * [Sample spaceship JSON](#sample-spaceship-json)
  * [Sample pilot JSON](#sample-pilot-json)
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

### Sample spaceship JSON:

```json
{
  "id": "f7d78abd-c9b3-41ab-bb90-e16ac4a15ce7",
  "name": "Millenium Falcon",
  "payload": 100,
  "crew": 3,
  "class": "Light freighter"
}
```

### Sample pilot JSON:

```json
{
  "id": "c77821c7-6b82-4d43-9837-2f7768610f45",
  "name": "Mando",
  "species": "Human",
  "profession": "Bounty hunter",
  "weapons": [
    "IB-94 blaster pistol",
    "Beskar spear",
    "Darksaber"
  ]
}
```

### Sample error JSON:

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
http://localhost:8080/api/spaceships
```

Swagger UI is available at:

```
http://localhost:8080
```

Pilot APIs are exposed at:

```
http://localhost:8081/api/pilots
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
