# From Rails to Spring Boot

Like Rails, Spring Boot also follows _Convention over Configuration_ principles.
This repository's goal is to focus on similarities and differences between both
frameworks in order to provide a quick guide for developers that are migrating
from one to another.

Contributions are welcome!

[Pre-requisite](#pre-requisite)\
[Maven instalation](#maven-instalation)\
[Spring Boot instalation](#spring-boot-cli-instalation)\
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

## Spring Boot CLI instalation

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

## App Initialization

Once Spring Boot CLI is installed, we can use `spring init` command to a start a
new Spring Boot project (just like we would do with `rails new`):

```
# rails new <app_name>
spring init <app_name> -d=web,data-jpa,h2,thymeleaf
```
`-d` allows us to specify dependencies we want to set up. In this example we're
using the ones that are aimed at a basic web project:
- [web](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-web):
Build web, including RESTful, applications using Spring MVC. Uses Apache Tomcat as the default embedded container.
- [data-jpa](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-data-jpa):
Persist data in SQL stores with Java Persistence API using Spring Data and Hibernate.
- [h2](https://mvnrepository.com/artifact/com.h2database/h2): Provides a fast
in-memory database that supports JDBC API, with a small
(2mb) footprint. Supports embedded and server modes as well as a browser based
console application.
- [thymeleaf](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-thymeleaf): Server-side Java template engine

[Example of Spring Boot
initialization](https://github.com/lidimayra/from-rails-to-spring-boot/commit/310ae4766254c3b18c6fe144cf7eacee49dcc515).

Note that a class was created named as `DemoApplication.java` in
`src/main/java/com/example/<app_name>/` ([Example](https://github.com/lidimayra/from-rails-to-spring-boot/blob/310ae4766254c3b18c6fe144cf7eacee49dcc515/myapp/src/main/java/com/example/myapp/DemoApplication.java))

By default, Spring uses [Maven](https://maven.apache.org/) as the project
management tool. After running the command above, dependencies can be found in
`pom.xml` file, at the root directory.

Install dependencies specified in `pom.xml` by using Maven:

```
# bundle install
mvn clean install
```

Start the server using `spring-boot:run`, a task that's provided by Maven
plugin:
```
# rails s
mvn spring-boot:run
```

Now application can be accessed at http://localhost:8080/. At this point, an
error page will be rendered, as there are no controllers defined so far.

## Controllers and views

In Spring Boot, there is no such thing as the rails generators. Also, there
is no file like _routes.rb_, where all routes are specified in a single place.

Write the controller inside `<app_name>/src/main/java/<package_name>`:

```java
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FooController {
    @GetMapping("/foo")
    public String index() {
        return "bar";
    }
}
```

The `@GetMapping` annotation ensures that GET requests performed to `/foo` will be
mapped to the method declared right after it (there is no file similar to
Rails' routes.rb in Spring Boot. Routes are defined alongside with its methods).

Because of Thymeleaf, by returning the String "bar", the application will look
for an HTML file of the same name in `src/main/resources/templates/`

Create the following page:
_bar.html_
```html
<p>FooBar</p>
```
[Example](https://github.com/lidimayra/from-rails-to-spring-boot/commit/13d195c)

Now, if we run the application with `mvn spring-boot:run` command and access
it at `http://localhost:8080/foo`, we'll see the _bar.html_ page being rendered.

## Project Structure

At this point, we have the initial structure of a Maven project.

- Main application code is placed in
  [src/main/java/](https://github.com/lidimayra/from-rails-to-spring-boot/tree/13d195c/myapp/src/main/java)
- Resources are placed in [src/main/resources](https://github.com/lidimayra/from-rails-to-spring-boot/tree/13d195c/myapp/src/main/resources)
- Tests code is placed in
  [src/test/java](https://github.com/lidimayra/from-rails-to-spring-boot/tree/310ae47/myapp/src/test/java)

In the root directory, we have the pom file:
[pom.xml](https://github.com/lidimayra/from-rails-to-spring-boot/blob/47070ef50056a763fdfeba46a8c8da2034de6118/myapp/pom.xml).
This is the Maven build specification. Like in Rails Gemfile, it contains the
project's dependencies declarations.
