# TMA Movie Exercise Interview Challenge

The goal of the exercise is to make a movie application.

All move images are stored in https://image.tmdb.org/

## Requirements

For building and running the application you need:

- [JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [Maven 3](https://maven.apache.org)
- [Node 10](https://nodejs.org/en/)

## Development

### Building the application

```shell
mvn clean install
```

### Running the application
#### Running the application 

The application supports to work on multiple profiles
- local - for local local (running in port 9080 and using H2 databse)
- dev - for local development (running in port 9080 and using MariaDB databse)
- prod - for local production (running in port 8080 and using MariaDB databse)

Note: Application will use the local profile by default.

```shell
mvn clean spring-boot:run -Plocal
```

## Technology 
### Server
+ Tomcat server
+ Spring Boot
+ Spring REST DATA
+ Spring JPA

### Client
+ Angular 8
+ Bootstrap 4

## Application Architecture
- client - Angular 8 Single Page Application that provides the Movie UI
- server - REST API for Movie Manager Exercise
