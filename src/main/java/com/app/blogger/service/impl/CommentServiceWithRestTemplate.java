package com.app.blogger.service.impl;

import com.app.blogger.model.Comment;
import com.app.blogger.payload.CommentDto;
import com.app.blogger.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class CommentServiceWithRestTemplate implements CommentService {
    @Autowired
    private RestTemplate restTemplate;

    @Value("${app.blogger.comment.url}")
    private String commentServiceUrl;

    @Override
    public ResponseEntity<Comment> createComment(Long postId, Long userId, CommentDto commentDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        headers.set("userId", String.valueOf(userId));
        HttpEntity<CommentDto> entity = new HttpEntity<>(commentDto, headers);
        ResponseEntity<Comment> commentResponseEntity = restTemplate.exchange(commentServiceUrl + "/posts/" + postId, HttpMethod.POST, entity, Comment.class);
        return commentResponseEntity;
    }

    @Override
    public ResponseEntity<List<Comment>> getCommentsByPostId(Long postId) {
        return restTemplate.exchange(commentServiceUrl + "/posts/" + postId + "/comments", HttpMethod.GET, null, new ParameterizedTypeReference<>() {
        });
    }

    @Override
    public ResponseEntity<Comment> getCommentById(Long postId, Long id) {
        return restTemplate.getForEntity(commentServiceUrl + "/posts/" + postId + "/comments/" + id, Comment.class);
    }

    @Override
    public ResponseEntity<Comment> updateComment(Long postId, Long id, CommentDto commentDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
//        headers.set("userId", String.valueOf(userId));
        HttpEntity<CommentDto> entity = new HttpEntity<>(commentDto, headers);
        ResponseEntity<Comment> commentResponseEntity = restTemplate.exchange(commentServiceUrl + "/posts/" + postId + "/" + id, HttpMethod.PUT, entity, Comment.class);
        return commentResponseEntity;
    }

    @Override
    public void deleteComment(Long postId, Long id) {
        restTemplate.delete(commentServiceUrl + "/posts/" + postId + "/" + id);
    }
}
