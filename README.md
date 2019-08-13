# Movie Exercise Challenge

The goal of the exercise is to make a fullstack movie application within 1 week.

<b>Requirements:</b>

<ul>
 <li>Create <b>technical design doc</b> to describe solution to a given execise</li>
 <li>Create <b>client side</b>:
  <ul>
   <li>Create the layout exactly like the homepage design below</li>
   <li>Font-family is Helvetica</li>
   <li>The top banner needs to be a carousel (a slideshow with 3 images)</li>
   <li>Request the Get Popular, Get Top Rated, Get Upcoming APIs and populate the data into the content grid view, scroll down the grid view to see more movies, select Popular/Top Rated/Upcoming to see the relevant content grid view.
</li>
   <li>Request the Get Movie List or Get TV List APIs to get the genres and fill up to dropdown Genre</li>
   <li>Optional
    <ul>
     <li>Create unit tests to verify your tasks is a plus</li>
     <li>UI with a nice looking and user-friendly design is a plus</li>
     <li>The pages should be responsive on different browsers is a plus</li>
     <li>Design your web app to close as much as your production code is a plus</li>
    </ul>
   </li>
  </ul>
 </li>
<li>Delivery</b>:
 <ul>
   <li>The source code should be published on Github with a ReadMe.md file to describe the assignment project information including third parties, how to build and run the web app.</li>
   <li>This assignment is designed to gauge your skills and give us an idea of how you approach tasks relevant to the Fullstack developer role. It would be great if you could send this over to us within 7 days</li>
  </ul>
 </li>
<li>Create <b>server side</b>:
 <ul>
   <li>Write UTs + ITs to verify tasks</li>   
   <li>Optional
    <ul>
     <li>Caching</li>
     <li>Performance Testing</li>
    </ul>
   </li>
  </ul>
 </li>
</ul>

## Application Architecture

<ul>
 <li>Project structure
  <ul>
   <li>client - Single page application using Angular 8 as front-end technology to develop Movie UI</li>
   <li>server - CRUD RESTFul APIs for Movie Management System</li>
  </ul>
 </li>
<li>Requirement: <a href="https://drive.google.com/file/d/1OMJ4xo13Y3Kkug1nutvEPMeBc548WwJC/view?usp=sharing" target="_blank">link</a></li>
<li>Technical design: <a href="https://docs.google.com/document/d/1-bVlU4_XlYVfjtpYgp1qjhqRhswRfVWut9eiBeEZLfg/edit#" target="_blank">link</a></li>
</ul>

## Output

#### Configure Movie data using GUI
By default, there are 3 movies are populated by the server side when running.
To populate more movies for testing, accessing to http://localhost:9080/#/movie-management then click on 'populate movie data' table (wait for 1 minute).

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

## Technology Stack
### Server
+ Tomcat server
+ Spring Boot
+ Spring REST DATA
+ Spring JPA

### Client
+ Angular 8
+ Bootstrap 4

## Using the application

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

<b>Application profiles</b>

The application supports to work on multiple profiles
- local - for local local (running on port 9080 and using H2 databse)
- dev - for local development (running on port 9080 and using MariaDB databse)
- prod - for local production (running on port 8080 and using MariaDB databse)

Note: Application will use the local profile by default.

#### Start the backend server

```shell 
server > mvn clean spring-boot:run -Plocal
```
the server running on port 9080

#### Start the client

```shell 
client > npm run start
```
Visit to http://localhost:4200 to view the home page

#### Using H2 database in local development

in-memory database running inside application in local mode, access it at http://localhost:9080/h2-console/ by deault

#### Using HAL Browser 

Access it at http://localhost:9080/api/v1

### Running tests

1. Integration tests

```shell 
server > mvn integration-test
```

2. UI tests

```shell 
client > npm run test
```
