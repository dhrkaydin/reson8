# RESON8 Backend

#### About
Back-end for RESON8, a music practice tracker. Right now it's just a CRUD API for a dockerized MySQL database.

#### Tech Stack
Java 11, Spring Boot, Docker, Maven, JPA, Hibernate, MySQL, JUnit, Mockito, Swagger, OpenAPI 

## Running/Building
### First boot
```
    mvn clean install  
    mvn clean test
    mvn spring-boot:run
```

### Setting up the Database
The database is dockerized and can be started with the following command:

```
    cd docker/database
    docker compose up -d
```

Without this database up and running the application will not start.

### Manually Testing

Aside from the JUnit tests, the resources folder in the root contains a postman collection to manually test the PracticeRoutine controller.
I'm already getting lazy updating it but it should be a good baseline.
Configure the DB connection in the application.properties file.