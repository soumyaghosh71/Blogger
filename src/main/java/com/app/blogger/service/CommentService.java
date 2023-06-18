package com.app.blogger.service;

import com.app.blogger.dto.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto createComment (Long postId, CommentDto commentDto);
    List<CommentDto> getCommentsByPostId(Long postId);
    CommentDto getCommentById(Long postId, Long id);
    CommentDto updateComment(Long postId, Long id, CommentDto commentDto);
    void deleteComment(Long postId, Long id);
}
