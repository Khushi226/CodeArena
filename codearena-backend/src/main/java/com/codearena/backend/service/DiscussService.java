package com.codearena.backend.service;

import com.codearena.backend.entity.*;
import com.codearena.backend.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiscussService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    public DiscussService(PostRepository postRepository,
                          CommentRepository commentRepository,
                          UserRepository userRepository){

        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
    }

    public Post createPost(Post post){
        return postRepository.save(post);
    }

    public List<Post> getAllPosts(){
        return postRepository.findAllByOrderByCreatedAtDesc();
    }

    public Post getPost(Long id){
        return postRepository.findById(id).orElseThrow();
    }

    public Comment addComment(Comment comment){
        return commentRepository.save(comment);
    }

    public List<Comment> getComments(Long postId){

    List<Comment> comments = commentRepository.findByPostId(postId);

    for(Comment c : comments){
        User user = userRepository.findById(c.getUserId()).orElseThrow();
        c.setUsername(user.getUsername());
    }

    return comments;
}

}