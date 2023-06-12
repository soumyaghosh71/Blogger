package com.app.blogger.controller;

import com.app.blogger.dto.PostDto;
import com.app.blogger.model.Post;
import com.app.blogger.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("posts")
public class PostController {

    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    // Create a post
    @PostMapping
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto) {
        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }

    // Get all posts
    @GetMapping
    public List<PostDto> getAllPosts() {
        return postService.getAllPosts();
    }

    // Get post by id
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Long id) {
        return ResponseEntity.ok(postService.getPostById(id));
    }

    // Update a post
    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable Long id) {
        PostDto updatePostDto = postService.updatePost(postDto, id);
        return new ResponseEntity<>(updatePostDto, HttpStatus.OK);
    }

    // Delete a post
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return new ResponseEntity<>("Post deleted successfully !", HttpStatus.OK);
    }
}
