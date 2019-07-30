# TMA Movie Exercise Interview Challenge

The goal of the exercise is to make a movie application.

All move images are stored in https://image.tmdb.org/

## Requirements

For building and running the application you need:

- [JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [Maven 3](https://maven.apache.org)
- [Node 10](https://nodejs.org/en/)

## Running the application locally

There are several ways to run a Spring Boot application on your local machine. One way is to execute the `main` method in the `de.codecentric.springbootsample.Application` class from your IDE.

Alternatively you can use the [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html) like so:

```shell
mvn spring-boot:run
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
