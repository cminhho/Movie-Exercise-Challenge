# TMA Movie Exercise Interview Challenge

The goal of the exercise is to make a movie application.

All move images are stored in https://image.tmdb.org/

## Application Architecture

Project structure

- client - Movie UI
- server - Movie server application



## How should the UI look?

#### Configure Movie data using GUI
1. Accessing to http://localhost:9080/#/movie-management then click on 'populate movie data' table (wait for 1 minute).

#### Movie Management Page
<img src="/screenshots/movie-mgmt-page-desktop.png" width="850">

#### Movie Page

<table border="0">
 <tr>
    <td><b style="font-size:30px">Desktop layout</b></td>
    <td><b style="font-size:30px">Mobile Layout</b></td>
 </tr>
 <tr>
    <td><img src="/screenshots/movie-main-page-desktop.png" width="350"></td>
    <td><img src="/screenshots/movie-main-page-mobile.png" width="350"></td>
 </tr>
</table>

## Tools used

For building and running the application we need:

- [JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [Maven 3](https://maven.apache.org)
- [Node 10](https://nodejs.org/en/)
- MariaDB
- InteliJ, Visual Studio Code

## Technology used
### Server
+ Tomcat server
+ Spring Boot
+ Spring REST DATA
+ Spring JPA

### Client
+ Angular 8
+ Bootstrap 4

## Using the application

### Application profiles

The application supports to work on multiple profiles
- local - for local local (running on port 9080 and using H2 databse)
- dev - for local development (running on port 9080 and using MariaDB databse)
- prod - for local production (running on port 8080 and using MariaDB databse)

Note: Application will use the local profile by default.

### Using in produciton 

1. Start MariaDB/MySQL then create `moviedb` database

2. Build the application

```shell
mvn clean install
```
3. Run the application

```shell
cd server 
mvn clean spring-boot:run -Pprod
```

###  Using in Development

1. Start the backend server

```shell 
server > mvn clean spring-boot:run -Plocal
```
the server running on port 9080

2. Start the client

```shell 
client > npm run start
```
Visit to http://localhost:4200 to view the home page
