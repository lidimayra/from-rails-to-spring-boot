# From Rails to Spring Boot

A quick guide for developers migrating from Rails to Spring Boot.

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
spring init <app name> -d=web,data-jpa,h2,freemarker
```
`-d` allows us to specify dependencies we want to set up. In this example we're
using the ones that are aimed at a basic web project:
- [web](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-web):
Build web, including RESTful, applications using Spring MVC. Uses Apache Tomcat as the default embedded container.
- [data-jpa](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-data-jpa):
Persist data in SQL stores with Java Persistence API using Spring Data and Hibernate.
- [freemarker](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-freemarker):
Java library to generate text output (HTML web pages, e-mails,
configuration files, source code, etc.) based on templates and changing data.

By default, Spring uses [Maven](https://maven.apache.org/) as the default
project management tool. After running the command above, dependencies can be
found in `pom.xml` file, at the root directory.

Install dependencies specified at `pom.xml` by using maven:

```
# bundle install
mvn clean install
```
