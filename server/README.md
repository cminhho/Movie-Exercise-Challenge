## Development

To start your application in the dev profile, simply run:

    mvn clean spring-boot:run -Plocal # (H2 Databse)
    mvn clean spring-boot:run -Pdev # (MySQL)
    mvn clean spring-boot:run -Pprod # (MariaDB)

## Testing

To launch your application's tests, run:

    ./mvnw verify

## Using Docker

Start a mariadb database in a docker container, run:

    docker-compose -f src/main/docker/mariadb.yml up -d

Build a docker image for the app by running:

    ./mvnw -Pprod verify jib:dockerBuild
    or
    mvn clean package dockerfile:build

Then run:

    docker-compose -f docker-compose.yml up -d

.