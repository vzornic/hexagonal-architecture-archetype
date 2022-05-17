# Flowrspot

## Introduction

Flowrspot is application built as assignment for Povio. More details on assignment itself can be found in [ASSIGNMENT](ASSIGNMENT.md)

## Local Setup

### Prerequisites

##### Java

Application is built with JDK 11, and it is required to be present in order for application to successfully run.

https://openjdk.java.net/install/

##### Maven

Maven version 3.x.x is used as build automation tool and is required in order to build and run project.

https://maven.apache.org/install.html

##### Docker

Docker is primarily used for integration tests (postgresql test container) however app also ships docker-compose which can be
used to simply start postgresql for local use and sonarqube.

https://docs.docker.com/get-docker/

#### PostgreSQL

App requires postgresql to be run on port 5432 with DB/Username/Password being `flowrspot`.
To avoid installing postgresql locally, use docker compose shipped with the app.

###### Docker compose

In order to simplify development, use this [docker-compose](docker/local/docker-compose.yml) file which will start PostgreSQL and SonarQube.

https://docs.docker.com/compose/install/

#### SonarQube (Optional)

SonarQube is only used as static code analysis tool and is not required for application itself. Within provided docker-compose it also contains service for SonarQube.

### Build & Run

##### Build
To build application itself run `mvn clean install` or just `make build`. This will also run unit & integration tests.

##### Run
In order to run application with standard local mode run the following:

```shell
java -jar -Dspring.profiles.active=local rest/target/rest-1.0-SNAPSHOT-exec.jar
```

If you're using IntelliJ, you can simply run application by navigating to the [RestApplication](rest/src/main/java/com/vzornic/flowrspot/rest/RestApplication.java) and press the button to start. 
It is important to note that you'll have to edit run configuration and make sure that `local` is set as active profile.


##### Swagger

The app will also start [swagger-ui](http://localhost:8080/swagger-ui/index.html#) page with ability to test APIs.

However, the login step is not (yet) available, so you'll have to log in via other clients first, obtain the token and
authorize in swagger using that token.


## Development & Architecture

The project is written by following principles of [Hexagonal Architecture (a.k.a Ports and Adapters)](https://en.wikipedia.org/wiki/Hexagonal_architecture_(software)).
Without speaking too much on hexagonal architecture but in order to explain the codebase it is required to describe the following modules:

##### Domain

Module holding all the domain models. This module should be easy to read and understand by anybody who understand
the business logic of the application.

Does not have any external dependencies!

##### API

This module specifies contracts to be used by business logic module. In hexagonal architecture world this module is containing all the output ports. 

Does not have any external dependencies!

##### Application

This module is the most important one in the entire project as it encapsulates the business logic of the application.

It also follows [Command pattern](https://en.wikipedia.org/wiki/Command_pattern) so all use cases are written using [UseCase class](core/application/src/main/java/com/vzornic/flowrspot/application/UseCase.java).

List of use cases can be found [here](core/application/src/main/java/com/vzornic/flowrspot/application/usecase). 

This module imports only `domain` and `api` modules and is not aware of any frameworks or actual implementations of ports (i.e postgresql).

In hexagonal architecture world this module is also known as input port.

##### API IMPL

This module contains implementations of ports (a.k.a adapters). In our use case, one adapter actually covered two ports due to
simplicity and ability to leverage architecture.

##### Rest

This module is basically the one who wires everything together. It has only two purposes:

1. Setting up the application layer and providing the port implementations to it (via ServiceRegistry in our case)
2. To expose application use cases to the users using HTTP protocol (Rest endpoints)

Finally, this module is the one that is exposed to the users.

## To Whom It May Concern

As this is assignment project, not all things I find nice and useful are implemented such as:

##### Use case execution listeners

- Embracing use case execution listeners for auditing use cases, monitoring use cases etc...
- Adding support for multi role users
- Better logging (or logging at all)


##### Images use case

The requirement about images I didn't fully understand. Not sure If it was actually a task to create a service for
uploading images to local env or S3. To keep it simple I just put path's as part of request.

##### Requests validation

Validation of request data was done in few use cases but could be enhanced a lot based on business requirements, i.e:

- username must be of certain format
- image path & url must be valid
- coordinates must be valid etc

Obviously would use same pattern of bean validators as used on these few built in javax validations.

##### Integration tests

While project has [several integration tests](rest/src/test-integration/java/com/vzornic/flowrspot/rest/it/testcases)
there should be many more covering other us cases along enhancing existing ones.

##### Exception handling

While project has [Global Exception Handler](rest/src/main/java/com/vzornic/flowrspot/rest/exception/GlobalExceptionHandler.java)
there should be more exceptions added.