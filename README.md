# Decision-Service

## About
The Decision Service evaluates business logic using an embedded 
Camunda DMN engine. It executes decision tables to support dynamic, 
rules-based workflows. As a centralized and widely recognized 
component across the company, it ensures consistent and 
transparent decision-making.

## System Requirements

- Java 21
- Apache Maven 3.9.9
- Docker (if running the service within the Docker container)

## Configuration

### Database
Configure database connection in `application.yaml` file:
```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/evidentor
    driverClassName: org.postgresql.Driver
    username: postgres
    password: postgres
```

### DMN Decision
Configure DMN decisions in `application.yaml` file (example for `room-access` decision model):
```yaml
dmn:
  decision:
    room-access:
      id: Decision_1lgk6e3
      name: RoomAccessDecision.dmn
```

## How to Install?

### 1. Clone the repository
```shell
git clone https://github.com/Evidentor/Decision-Service.git
cd Decision-Service
```

### 2. Install dependencies
```shell
mvn clean install
```

## How to Run?

### Run with java
```shell
java --enable-preview -jar target/*.jar
```

### Run with docker
#### 1. Build the docker image
```shell
docker build -t decision-service .
```

#### 2. Create the docker container
```shell
docker run -d --network host --name decision-service decision-service:latest
```

#### 3. Stop the docker container
```shell
docker stop decision-service
```

#### 4. Start the docker container
```shell
docker start decision-service
```

## How to Test?
```shell
mvn test
```
