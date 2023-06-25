package com.app.blogger.controller;

import com.app.blogger.model.Comment;
import com.app.blogger.payload.CommentDto;
import com.app.blogger.service.CommentService;
import com.app.blogger.service.impl.CommentServiceWithRestTemplate;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts/{postId}/comments")
public class CommentController {

    private CommentService commentService;

    public CommentController(CommentServiceWithRestTemplate commentService) {
        this.commentService = commentService;
    }

    @PostMapping()
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Comment> createComment(@PathVariable Long postId, @RequestHeader(name = "userId") Long userId, @Valid @RequestBody CommentDto commentDto) {
        return commentService.createComment(postId, userId, commentDto);
    }

    @GetMapping
    public ResponseEntity<List<Comment>> getCommentsByPostId(@PathVariable Long postId) {
        return commentService.getCommentsByPostId(postId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Comment> getCommentById(@PathVariable Long postId, @PathVariable Long id) {
        return commentService.getCommentById(postId, id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Comment> updateComment(@PathVariable Long postId, @PathVariable Long id, @Valid @RequestBody CommentDto commentDto) {
        return commentService.updateComment(postId, id, commentDto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteComment(@PathVariable Long postId, @PathVariable Long id) {
        commentService.deleteComment(postId, id);
        return new ResponseEntity<>("Comment deleted successfully !", HttpStatus.OK);
    }
}
