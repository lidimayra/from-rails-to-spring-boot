package com.example.myapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

@Controller
public class BlogController {

    @Autowired
    private PostRepository postRepository;

    @GetMapping("/posts")
    public String listPosts() {
        return "blog/index";
    }

    @GetMapping("/posts/new")
    public String newPost() {
        return "blog/new";
    }

    @PostMapping("/posts")
    public String createPost(Post post) {
        postRepository.save(post);
        return "redirect:/posts";
    }

    @GetMapping("/posts/{postId}")
    public String showPost() {
        return "blog/show";
    }

    @GetMapping("/posts/{postId}/edit")
    public String editPost() {
        return "blog/edit";
    }

    @PatchMapping("/posts/{postId}")
    public String updatePost() {
        //TODO: logic responsible for updating a post
        return null;
    }

    @DeleteMapping("/posts/{postId}")
    public String deletePost() {
        //TODO: logic responsible for deleting a post
        return null;
    }
}
