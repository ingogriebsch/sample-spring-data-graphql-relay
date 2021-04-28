# Spring Data GraphQL Relay sample
[![Actions Status](https://github.com/ingogriebsch/sample-spring-data-graphql-relay/workflows/build/badge.svg)](https://github.com/ingogriebsch/sample-spring-data-graphql-relay/actions)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=de.ingogriebsch.samples%3Asample-spring-data-graphql-relay&metric=alert_status)](https://sonarcloud.io/dashboard?id=de.ingogriebsch.samples%3Asample-spring-data-graphql-relay)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=de.ingogriebsch.samples%3Asample-spring-data-graphql-relay&metric=coverage)](https://sonarcloud.io/dashboard?id=de.ingogriebsch.samples%3Asample-spring-data-graphql-relay)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=de.ingogriebsch.samples%3Asample-spring-data-graphql-relay&metric=sqale_rating)](https://sonarcloud.io/dashboard?id=de.ingogriebsch.samples%3Asample-spring-data-graphql-relay)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=de.ingogriebsch.samples%3Asample-spring-data-graphql-relay&metric=reliability_rating)](https://sonarcloud.io/dashboard?id=de.ingogriebsch.samples%3Asample-spring-data-graphql-relay)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=de.ingogriebsch.samples%3Asample-spring-data-graphql-relay&metric=security_rating)](https://sonarcloud.io/dashboard?id=de.ingogriebsch.samples%3Asample-spring-data-graphql-relay)
[![License](http://img.shields.io/:license-apache-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0.html)

This sample shows you how to implement a GraphQL relay in a Spring Data environment.

## How to build and run
If you want to see the implementation in action, simply follow these steps:

*   First, make sure that you have Java 8 or later installed and an established Internet connection.
*   Then, clone this Git repository an `cd` into the project folder. 
*   Now invoke `./mvnw spring-boot:run` and wait a moment.

After the project is built and the Spring Boot service is running you can hit the following urls to act with the service:

*   [http://localhost:8080/graphiql](http://localhost:8080/graphiql) allows to access the interactive GraphQL UI.
*   [http://localhost:8080/voyager](http://localhost:8080/voyager) allows to access the GraphQL Voyager UI.

Have a look into the implementation to understand how the data is resolved.

## Used frameworks
A collection of the mainly used frameworks in this project. 
There are more, but they are not that present inside the main use case, therefore they are not listed here.

*   [GraphQL Java Kickstart](https://www.graphql-java-kickstart.com/)
*   [Spring Data JPA](https://docs.spring.io/spring-data/jpa/docs/2.4.2/reference/html/)

## License
This code is open source software licensed under the [Apache 2.0 License](https://www.apache.org/licenses/LICENSE-2.0.html).
