package com.codearena.backend.controller;

import com.codearena.backend.entity.*;
import com.codearena.backend.service.DiscussService;

import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

import java.util.List;

@RestController
@RequestMapping("/discuss")
@CrossOrigin(origins = "http://localhost:5173")
public class DiscussController {

    private final DiscussService discussService;

    public DiscussController(DiscussService discussService) {
        this.discussService = discussService;
    }

    @PostMapping
    public Post createPost(@RequestBody Post post) {
        return discussService.createPost(post);
    }

    @GetMapping
    public List<Post> getPosts() {
        return discussService.getAllPosts();
    }

    @GetMapping("/{id}")
    public Post getPost(@PathVariable Long id) {
        return discussService.getPost(id);
    }

    @PostMapping("/{id}/comment")
    public Comment addComment(@PathVariable Long id,
            @RequestBody Comment comment) {

        if (comment.getContent() == null || comment.getContent().trim().length() < 10) {
            throw new RuntimeException("Comment must be at least 10 characters");
        }

        comment.setPostId(id);

        Comment savedComment = discussService.addComment(comment);

        return savedComment;
    }

    @GetMapping("/{id}/comments")
    public List<Comment> getComments(@PathVariable Long id) {
        return discussService.getComments(id);
    }

}