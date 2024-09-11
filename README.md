# The tc-ff-payments-api

## Project Overview

Fiap Tech Fast Food is a system designed to manage a neighborhood fast food restaurant. The system allows for user registration, product management, order creation, and payment processing. It is built to be resilient to failures and scalable.

This service is responsible for
 - Payment processing
 - Payment gateway integration
 - Notification of payment approval
 - Notification of payment rejection

 The system is built to be fault resilient and scalable, allowing the payments to operate efficiently even during periods of high order volume.

## Badges
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=tech-challenge-fiap-5soat_tc-ff-payments-api&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=tech-challenge-fiap-5soat_tc-ff-payments-api)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=tech-challenge-fiap-5soat_tc-ff-payments-api&metric=bugs)](https://sonarcloud.io/summary/new_code?id=tech-challenge-fiap-5soat_tc-ff-payments-api)
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=tech-challenge-fiap-5soat_tc-ff-payments-api&metric=code_smells)](https://sonarcloud.io/summary/new_code?id=tech-challenge-fiap-5soat_tc-ff-payments-api)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=tech-challenge-fiap-5soat_tc-ff-payments-api&metric=coverage)](https://sonarcloud.io/summary/new_code?id=tech-challenge-fiap-5soat_tc-ff-payments-api)
[![Duplicated Lines (%)](https://sonarcloud.io/api/project_badges/measure?project=tech-challenge-fiap-5soat_tc-ff-payments-api&metric=duplicated_lines_density)](https://sonarcloud.io/summary/new_code?id=tech-challenge-fiap-5soat_tc-ff-payments-api)

Api responsible by manage the flow of paymetns in our neighborhood fastfood.

## Technology stack

This API was built using [Java](https://www.java.com/) and several tools:
- [Spring Boot](https://spring.io/projects/spring-boot) - Framework for creating stand-alone, production-grade Spring-based Applications
- [Maven](https://maven.apache.org/) - Dependency management and build automation tool
- [PostgreSQL](https://www.postgresql.org/) - Open-source relational database
- [Springdoc OpenAPI UI](https://springdoc.org/) - API documentation tool for Spring Boot projects
- [JUnit](https://junit.org/junit5/) - Testing framework for Java
- [Mockito](https://site.mockito.org/) - Mocking framework for unit tests
- [MapStruct](https://mapstruct.org/) - Code generator for bean mappings
- [Jackson](https://github.com/FasterXML/jackson) - JSON parser for Java
- [Amazon SQS](https://aws.amazon.com/sqs/) - Fully managed message queuing service
- [Feign](https://github.com/OpenFeign/feign) - Java to HTTP client binder
- [Spring Security](https://spring.io/projects/spring-security) - Powerful and highly customizable security framework
- [Lombok](https://projectlombok.org/) - Library to reduce boilerplate code
- [Docker](https://www.docker.com/) - Platform for developing, shipping, and running applications in containers
- [make](https://www.gnu.org/software/make/) - Task automation tool
- [kubernetes](https://kubernetes.io/pt-br/) - Container orchestration

## Architecture

For a demonstration of the architecture, visit: [Architecture Video](https://drive.google.com/file/d/1NheE489Ma2W28Jvz3ZzRNAWCeHTrwVbm/view?usp=sharing)

## Running the Application

### Using Docker

The app can be started using Docker. You can use the pre-defined actions in the Makefile.

#### Build Image

To build an image from the project to push to a registry, use the command below:

```sh
make build-image
```

This command will generate an image with the tag: `tc-ff-payments-api`.

### Development

Before run the application, you need to export the variables below:

```sh
export AWS_ACCESS_KEY_ID=test
export AWS_SECRET_ACCESS_KEY=test
```

Make sure that you update the datasource url with your local ip address in the file `resources/application.yaml`.
To run in development for debugging or improvement, use the command:

```sh
make start-local-development &
```

And run that command in another terminal:

```sh
make run
```

This command will start a container with hot-reload for any code modifications, including a container with an instance of MongoDB.

To stop the container, execute:

```sh
make stop-local-development
```

### Testing

Locally, you can use the command below:

```sh
make test
```

This command will run all unit tests.