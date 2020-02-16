# From Rails to Spring Boot

Like Rails, Spring Boot also follows _Convention over Configuration_ principles.
This repository's goal is to focus on similarities and differences between both
frameworks in order to provide a quick guide for developers that are migrating
from one to another.

Contributions are welcome!

[Pre-requisite](#pre-requisite)\
[Maven instalation](#maven-instalation)\
[Spring Boot instalation](#spring-boot-cli-instalation)\
[App Initialization](#app-initialization)\
[Controllers & Views](#controllers-and-views)\
[Project Structure](#project-structure)\
[RESTful routes](#restful-routes)\
[From Rails Models to Spring Entities](#from-rails-models-to-spring-entities)\
[Performing a creation through a web interface](#performing-a-creation-through-a-web-interface)\
[Displaying a collection of data](#displaying-a-collection-of-data)\
[Editing and Updating data](#editing-and-updating-data)\
[Showing a Resource](#showing-a-resource)\
[Destroying a Resource](#destroying-a-resource)

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

## RESTful routes

Let's say we want to build a blog containing the seven RESTful actions (index,
new, create, show, edit and destroy) for posts path. In Rails, we could achieve
that by defining `resources: :posts` in `routes.rb` file.

As mentioned previously, Spring Boot does not have a central point where
all routes are specified. Those are defined in the controllers instead.

We've already seen an example using `@GetMapping` annotation to demonstrate the
definition of a route that uses `GET` method. Similarly, Spring supports other
four inbuilt annotations for handling different types of HTTP request methods:
`@PostMapping`, `@PutMapping`, `@DeleteMapping` and `@PatchMapping`.

Example of these concepts being applied for the blog posts can be found in [here](https://github.com/lidimayra/from-rails-to-spring-boot/commit/101611c).

## From Rails Models to Spring Entities

In order to represent a Post in the application-level, we'll need to define it
as an Spring JPA Entity (very similar to the way it would be done with a Model
in Rails).

```java
@Entity // Designate it as a JPA Entity
public class Post {

    @Id // Mark id field as the entity's identity
    @GeneratedValue(strategy = GenerationType.AUTO) // Value will be automatically provided
    private Long id;
    private String title;
    private String content;

    public Long getId() { ... }

    public void setId(Long id) { ... }

    public String getTitle() { ... }

    public void setTitle(String title) { ... }

    public String getContent() { ... }

    public void setContent(String content) { ... }
}
```

Spring Data JPA provides some built-in methods to manipulate common data
persistence operations through the usage of repositories in a way that's very
similar to Rails' ActiveRecord. So, to work with Post data, a PostRepository must
be implemented as well:

```java
public interface PostRepository extends JpaRepository<Post, Long> {
}
```

JpaRepository interface takes to params, in this scenario: `Post` and `Long`.
`Post` because it is the entity that will be used and `Long` because that's the
type of `Post`'s identity (ID).

This interface will be automatically implemented at runtime.

Whole example can be found [in
here](https://github.com/lidimayra/from-rails-to-spring-boot/commit/e755e5a).

## Performing a creation through a web interface

Next step is adding a form to submit posts to the blog.
At this point, we already have the
[templates/blog/new.html](https://github.com/lidimayra/from-rails-to-spring-boot/blob/101611c7a5c5321169e492ed19381df5c1b12c76/myapp/src/main/resources/templates/blog/new.html)
file containing a single line in it.

Using Thymelaf, we can do that with the following approach:

```html
<!DOCTYPE html SYSTEM
"http://www.thymeleaf.org/dtd/xhtml1-strict-thymeleaf-4.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org">
    <body>
        <p>New Post</p>
        <form method="POST" action="/posts">
            <label for="title">Title:</label>
            <input type="text" name="title" size="50"></input><br/>
            <label for="content">Content:</label><br/>
            <textarea name="content" cols="80" rows="5"></textarea>
            <br/>
            <input type="submit"></input>
        </form>
    </body>
</html>
```

And then, `BlogController` must be adjusted to permit that when a POST request
to `/posts` is performed, the submitted params must be used to create this new
post.

```java
@Controller
public class BlogController {

    @Autowired
    private PostRepository postRepository;

    @GetMapping("/posts")
    public String listPosts() { ... }

    @PostMapping("/posts")
    public String createPost(Post post) {
        postRepository.save(post); // Use JPA repository built-in method.
        return "redirect:/posts"; // redirect user to /posts page.
    }
}
```

See whole implementation [in
here](https://github.com/lidimayra/from-rails-to-spring-boot/commit/b7301838feb251851874fc72704e0100d2e8fa0e#diff-926ef30f0a8789410c4e35200aacb000).

## Displaying a collection of data

We'll make changes to `/posts` page so it will list all posts that are
recorded in the database.

`BlogController`'s method that's associated to this route needs to be adjusted
for making this data available to the view:

```java
@GetMapping("/posts")
public String listPosts(Model model) {
    List<Post> posts = postRepository.findAll();
    model.addAttribute("posts", posts);
    return "blog/index";
}
```

In Spring, Models are used to hold application data and make it available to the
view (like instance variables in Rails). In this example, we're adding the list
of posts to a key named `posts`, so we can access it from the template.

Following code must be implemented to
[templates/blog/index.html](https://github.com/lidimayra/from-rails-to-spring-boot/blob/101611c7/myapp/src/main/resources/templates/blog/index.html):
```html
<!DOCTYPE html SYSTEM "http://www.thymeleaf.org/dtd/xhtml1-strict-thymeleaf-4.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org">

<h1>Blog</h1>

<dl th:each="post : ${posts}">
    <dt>
        <span th:text="${post.title}">Title</span>
    </dt>

    <dd>
        <span th:text="${post.content}">Content</span>
    </dd>
</dl>

<a th:href="@{/posts/new}">Submit a new post</a>

```

See implementation [in
here](https://github.com/lidimayra/from-rails-to-spring-boot/commit/b96ce2c).

Now, accessing application at http://localhost:8080/posts, it is possible to
list and to submit posts using the features implemented so far. Similar approach
can be applied to implement the other actions.

## Editing and Updating data

Now we want to enable editing/updating functionalities.
Following changes must be made to `editPost()` method in `BlogController`:

```java
@getMapping("/posts/{postId}/edit")
public String editPost(@PathVariable("postId") long id, Model model) {
    Post post = postRepository.findById(id)
          .orElseThrow(() -> new IllegalArgumentException("Invalid Post
Id:" + id)); // Ensure post exists before rendering edit form

    model.addAttribute("post", post); // enable post to be consumed by edit template

    return "blog/edit"; // render edit template
}
```

Note that the `id` parameter contains a `@PathVariable` annotation. This
annotation indicates that this param must receive a value that's embedded in the
path. In this case, `id` param will have the value that's passed as `postId`
when performing a request to `/posts/{postId}/edit`. Just like we would do by
calling `params[postId]` in Rails.

Then, we must implement the edit form:


```html
<!DOCTYPE html SYSTEM "http://www.thymeleaf.org/dtd/xhtml1-strict-thymeleaf-4.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org">
    <body>
        <p>Edit Post</p>
        <form th:method="post"
              th:action="@{/posts/{id}(id=${post.id})}"
              th:object="${post}">

            <input type="hidden" name="_method" value="patch" />
            <label for="title">Title:</label>
            <input type="text" name="title" size="50" th:field="${post.title}"></input>
            <br/>
            <label for="content">Content:</label>
            <br/>
            <textarea name="content" cols="80" rows="5" th:field="${post.content}"></textarea>
            <br/>
            <input type="submit"></input>
        </form>
    </body>
</html>
```

This is enough to render an edit form. Thanks to Thymeleaf we can use `th:field`
to map Post fields and provide a pre-populated form to the final user. At
this point, edit form can be accessed at
`https://localhost:8080/posts/<post_id>/edit`.

However, as the update behavior wasn's implemented yet, it is still pointless to
submit this form.

In order to implement it, the following changes are required in the
`BlogController`:

```java
@PatchMapping("/posts/{postId}")
public String updatePost(@PathVariable("postId") long id, Model model, Post post) {
    Post recordedPost = postRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Invalid Post Id:" + id));

    recordedPost.setTitle(post.getTitle());
    recordedPost.setContent(post.getContent());
    postRepository.save(recordedPost);

    model.addAttribute("posts", postRepository.findAll());
    return "blog/index";
}
```

After these changes, posts are ready to be edited through the UI. An edit link
can also be added to `posts/index` to enable edit form to be easily accessed:

```html
<a th:href="@{/posts/{id}/edit(id=${post.id})}">Edit</a>
```

This implementation can be seen [in
here](https://github.com/lidimayra/from-rails-to-spring-boot/commit/2960884).

## Showing a Resource

Given what've done so far, there is nothing new in implementing the feature
responsible for showing a resource.

Changes to be performed to the controller:
```java
@GetMapping("/posts/{postId}")
public String showPost() {
public String showPost(@PathVariable("postId") long id, Model model) {
    Post post = postRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Invalid Post Id:" + id));

    model.addAttribute("post", post);

    return "blog/show";
}
```

And a simple template to display title and content for a single post:
```html
<!DOCTYPE html SYSTEM "http://www.thymeleaf.org/dtd/xhtml1-strict-thymeleaf-4.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org">

    <body>
        <h1 th:text="${post.title}"></h1>
        <hr>
        <p th:text="${post.content}"></p>

        <p><a th:href="@{/posts/{id}/edit(id=${post.id})}">Edit</a></p>
        <hr>
        <a th:href="@{/posts/}">Go back to posts</a>
    </body>
</html>
```

These changes enable post details to be available at `https://localhost:8080/posts/<post_id>`.

We can also add a link at posts index to allow direct access to show:
```html
<a th:href="@{/posts/{id}/(id=${post.id})}">Show</a>
```

Implementation can be seen [in
here](https://github.com/lidimayra/from-rails-to-spring-boot/commit/dd98c1d).

## Destroying a Resource

Now, we'll add the feature to remove a post.

In `BlogController`:
```java
@GetMapping("/posts/{postId}/delete")
public String deletePost(@PathVariable("postId") long id, Model model) {
    Post recordedPost = postRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Invalid Post Id:" + id));

    postRepository.delete(recordedPost);
    model.addAttribute("posts", postRepository.findAll());
    return "blog/index";
}
```

Note that we're using GET method in here. That's because in this example, our
app is a monolith and DELETE method is not supported by the browsers. In order to
keep things simple and avoid the addition of a form with a hidden field to
handle this method (like we did when updating), this one is being used as a GET.
If this was an API, `@DeleteMapping` would be the ideal option.

And then we can add a link to delete in index page:

```html
<a th:href="@{/posts/{id}/delete(id=${post.id})}">Delete</a>
```

Now it is possible to access https://localhost:8080/posts and delete each post
by using the delete link that's displayed below it.

Implementation can be found [here](https://github.com/lidimayra/from-rails-to-spring-boot/commit/cb38bf7).
