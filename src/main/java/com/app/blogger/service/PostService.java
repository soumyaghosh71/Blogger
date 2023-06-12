package com.app.blogger.service;

import com.app.blogger.dto.PostDto;
import com.app.blogger.model.Post;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto);

    List<PostDto> getAllPosts();

    PostDto getPostById(Long id);

    PostDto updatePost(PostDto postDto, Long id);

    void deletePost(Long id);
}
