# Book Management Project

- Build status [![CircleCI](https://circleci.com/gh/MiDiu98/BookManagement.svg?style=svg)](https://circleci.com/gh/MiDiu98/BookManagement)
- Test coverage [![codecov](https://codecov.io/gh/MiDiu98/BookManagement/branch/master/graph/badge.svg)](https://codecov.io/gh/MiDiu98/BookManagement)


```
The goal of this exercise is to write a small web application using Spring Boot Framework. 
The application allows you to handle books in a library. 
It should contain five pages, namely Login, Register, Books List (search, order, pagination), Book Details, Book Creation and Book Edition.
```

## Features
#### Authentication
- Register
- Login
#### Main Function
- Show all users
- Show all enable books with pagination
- Show disable books for admin
- Show all books for admin with pagination
- Show all my books
- Show detail profile
- Show detail book
- Search book by title and author
- Update, delete my account
- Create, update, delete my books
- Create, update, delete my comments

## Prerequisites
- Java
- Gradle
- PostGreSQL
- Postman to test API

## Connect PostgreSQL Database
### Init Database in PostgreSQL
- Create database 

### Connect to PostgreSQL from Spring
-  Change file application.properties
```
## Spring DATASOURCE (DataSourceAutoConfiguration & DataSourceProperties)
spring.datasource.driver-class-name=org.postgresql.Driver
spring.datasource.url=jdbc:postgresql://localhost:port/database
spring.datasource.username=yourUsername
spring.datasource.password=yourPassword

# The SQL dialect makes Hibernate generate better SQL for the chosen database
spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.PostgreSQLDialect
spring.jpa.properties.hibernate.jdbc.lob.non_contextual_creation=true

# Hibernate ddl auto (create, create-drop, validate, update)
spring.jpa.hibernate.ddl-auto = create (or update)
```

- Add dependencies to build.gradle
```
implementation('org.postgresql:postgresql')
compile("org.postgresql:postgresql:9.4-1206-jdbc42")
```

## Clean & Build Gradle Spring Boot Application with CLI
``> gradle clean build``

## Run Spring boot Application with CLI
``> gradle bootRun``
