package com.app.blogger.controller;

import com.app.blogger.payload.CommentDto;
import com.app.blogger.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts/{postId}/comments")
public class CommentController {

    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public ResponseEntity<CommentDto> createComment(@PathVariable Long postId, @RequestBody CommentDto commentDto) {
        return new ResponseEntity<>(commentService.createComment(postId, commentDto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CommentDto>> getCommentsByPostId(@PathVariable Long postId) {
        return ResponseEntity.ok(commentService.getCommentsByPostId(postId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable Long postId, @PathVariable Long id) {
        return ResponseEntity.ok(commentService.getCommentById(postId, id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable Long postId, @PathVariable Long id, @RequestBody CommentDto commentDto) {
        CommentDto updatedCommentDto = commentService.updateComment(postId, id, commentDto);
        return ResponseEntity.ok(updatedCommentDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable Long postId, @PathVariable Long id) {
        commentService.deleteComment(postId, id);
        return new ResponseEntity<>("Comment deleted successfully !", HttpStatus.OK);
    }
}
