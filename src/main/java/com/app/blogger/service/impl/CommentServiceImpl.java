package com.app.blogger.service.impl;

import com.app.blogger.exception.BlogAPIException;
import com.app.blogger.exception.ResourceNotFoundException;
import com.app.blogger.model.Comment;
import com.app.blogger.model.Post;
import com.app.blogger.payload.CommentDto;
import com.app.blogger.repository.CommentRepository;
import com.app.blogger.repository.PostRepository;
import com.app.blogger.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;
    private PostRepository postRepository;
    private ModelMapper modelMapper;

    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository, ModelMapper modelMapper) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CommentDto createComment(Long postId, CommentDto commentDto) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
        Comment comment = modelMapper.map(commentDto, Comment.class);
        comment.setPost(post);
        Comment newComment = commentRepository.save(comment);
        return modelMapper.map(newComment, CommentDto.class);
    }

    @Override
    public List<CommentDto> getCommentsByPostId(Long postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);
        return comments.stream().map(comment -> modelMapper.map(comment, CommentDto.class)).collect(Collectors.toList());
    }

    @Override
    public CommentDto getCommentById(Long postId, Long id) {
        Comment comment = validateComment(postId, id);
        return modelMapper.map(comment, CommentDto.class);
    }

    @Override
    public CommentDto updateComment(Long postId, Long id, CommentDto commentDto) {
        Comment comment = validateComment(postId, id);

        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());

        Comment updateComment = commentRepository.save(comment);
        return modelMapper.map(updateComment, CommentDto.class);
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
}
