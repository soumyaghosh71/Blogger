package com.app.blogger.service;

import com.app.blogger.model.Comment;
import com.app.blogger.payload.CommentDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CommentService {
    ResponseEntity<Comment> createComment(Long postId, Long userId, CommentDto commentDto);

    ResponseEntity<List<Comment>> getCommentsByPostId(Long postId);

    ResponseEntity<Comment> getCommentById(Long postId, Long id);

    ResponseEntity<Comment> updateComment(Long postId, Long id, CommentDto commentDto);

    void deleteComment(Long postId, Long id);
}
