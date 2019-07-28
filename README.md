# From Rails to Spring Boot

A quick guide for developers migrating from Rails to Spring Boot.

[Pre-requisite](#pre-requisite)\
[Maven instalation](#maven-instalation)\
[Spring Boot instalation](#spring-boot-instalation)\
[Spring app](#spring-app)

## Pre-requisite
[Java Development Kit 8](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)

## Maven instalation

### On Ubuntu
```
sudo apt update
sudo apt install maven
```

### On Mac OS (with Homebrew)
```
brew update
brew install maven
```

## Spring Boot instalation

### On Ubuntu (with SDKMAN)
```
curl "https://get.sdkman.io" | bash
source ~/.sdkman/bin/sdkman-init.sh
sdk install springboot
```

### On Mac (with Homebrew)
```
brew tap pivotal/tap
brew install springboot
```

## Spring app

App initialization:
```
# rails new <app_name>
spring init <app name> -d=web,data-jpa,h2,thymeleaf
```
`-d` allows us to specify dependencies we want to set up. In this example we're
using the ones that are aimed at a basic web project:
- [web](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-web):
Build web, including RESTful, applications using Spring MVC. Uses Apache Tomcat as the default embedded container.
- [data-jpa](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-data-jpa):
Persist data in SQL stores with Java Persistence API using Spring Data and Hibernate.
<<<<<<< HEAD
- [h2](https://mvnrepository.com/artifact/com.h2database/h2): Provides a fast
in-memory database that supports JDBC API, with a small
(2mb) footprint. Supports embedded and server modes as well as a browser based
console application.
- [thymeleaf](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-thymeleaf): Server-side Java template engine

By default, Spring uses [Maven](https://maven.apache.org/) as the project
management tool. After running the command above, dependencies can be found in
`pom.xml` file, at the root directory.

Install dependencies specified in `pom.xml` by using maven:

```
# bundle install
mvn clean install
```

Start the server:
```
# rails s
mvn spring-boot:run
```

This enables application to be available at http://localhost:8080/.
