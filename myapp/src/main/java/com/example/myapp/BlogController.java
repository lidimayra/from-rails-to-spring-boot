package com.example.myapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class BlogController {

    @Autowired
    private PostRepository postRepository;

    @GetMapping("/posts")
    public String listPosts(Model model) {
        List<Post> posts = postRepository.findAll();
        model.addAttribute("posts", posts);
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
    public String showPost(@PathVariable("postId") long id, Model model) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Post Id:" + id));

        model.addAttribute("post", post);

        return "blog/show";
    }

    @GetMapping("/posts/{postId}/edit")
    public String editPost(@PathVariable("postId") long id, Model model) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Post Id:" + id));

        model.addAttribute("post", post);

        return "blog/edit";
    }

    @PostMapping("/posts/{postId}")
    public String updatePost(@PathVariable("postId") long id, Model model, Post post) {
        Post recordedPost = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Post Id:" + id));

        recordedPost.setTitle(post.getTitle());
        recordedPost.setContent(post.getContent());
        postRepository.save(recordedPost);

        model.addAttribute("posts", postRepository.findAll());
        return "blog/index";

    }

    @GetMapping("/posts/{postId}/delete")
    public String deletePost(@PathVariable("postId") long id, Model model) {
        Post recordedPost = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Post Id:" + id));
        postRepository.delete(recordedPost);
        model.addAttribute("posts", postRepository.findAll());
        return "blog/index";
    }
}
