package com.app.blogger.service.impl;

import com.app.blogger.dto.CommentDto;
import com.app.blogger.exception.BlogAPIException;
import com.app.blogger.exception.ResourceNotFoundException;
import com.app.blogger.model.Comment;
import com.app.blogger.model.Post;
import com.app.blogger.repository.CommentRepository;
import com.app.blogger.repository.PostRepository;
import com.app.blogger.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;
    private PostRepository postRepository;

    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    @Override
    public CommentDto createComment(Long postId, CommentDto commentDto) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
        Comment comment = toEntity(commentDto);
        comment.setPost(post);
        Comment newComment = commentRepository.save(comment);
        return toDto(newComment);
    }

    @Override
    public List<CommentDto> getCommentsByPostId(Long postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);
        return comments.stream().map(comment -> toDto(comment)).collect(Collectors.toList());
    }

    @Override
    public CommentDto getCommentById(Long postId, Long id) {
        Comment comment = validateComment(postId, id);

        return toDto(comment);
    }

    @Override
    public CommentDto updateComment(Long postId, Long id, CommentDto commentDto) {
        Comment comment = validateComment(postId, id);

        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());

        Comment updateComment = commentRepository.save(comment);
        return toDto(updateComment);
    }

    @Override
    public void deleteComment(Long postId, Long id) {
        Comment comment = validateComment(postId, id);
        commentRepository.delete(comment);
    }

    private Comment validateComment(Long postId, Long id) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

        Comment comment = commentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", id));

        if (!comment.getPost().getId().equals(post.getId()))
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "The comment does not belong to the post.");
        return comment;
    }

    private Comment toEntity(CommentDto commentDto) {
        Comment comment = new Comment();
        comment.setId(commentDto.getId());
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());
        return comment;
    }

    private CommentDto toDto(Comment comment) {
        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setName(comment.getName());
        commentDto.setEmail(comment.getEmail());
        commentDto.setBody(comment.getBody());
        return commentDto;
    }
}
