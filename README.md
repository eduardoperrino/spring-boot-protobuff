# Spring Boot - Google Protocol Buffers
[![Build Status](https://travis-ci.org/eduardoperrino/spring-boot-protobuff.svg?branch=master)](https://travis-ci.org/eduardoperrino/spring-boot-protobuff)

## Introduction
Proof of concept to show the use of Google Protobuf inside a Spring Boot Application. Not for real uses only with demostration purposes.

The applicaction exposes a simple http endpoint at "/" on port "8082" that expects a body in JSON format that should contain two fields _id_ (integer) and _name_ (String).

This is an example using [httpie](http://github.com) client library:

```
http --json -f POST ":8082/" id=1 name=isaac
```
In case the request is successfully processed, the application will return a _200_ http response with the same received body in JSON format.

Internally, the application creates a file in disk per request using Google Protocol Buffers to store data.

## Prerequisites
* JDK 1.8+ installed (Oracle or OpenJDK is supported)
* docker installed
* docker-compose installed

## Build application
* ./mvnw clean install

## Run application
* docker-compose up

## Run application to development
* ./mvnw spring-boot:run

## Test application
* ./mvnw test
