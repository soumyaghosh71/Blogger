package com.app.blogger.service;

import com.app.blogger.payload.PostDto;
import com.app.blogger.payload.PostResponse;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto);

    PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);

    PostDto getPostById(Long id);

    PostDto updatePost(PostDto postDto, Long id);

    void deletePost(Long id);

    List<PostDto> getPostByCategory(Long categoryId);

    List<PostDto> searchPostsByTitle(String title, int pageNo, int pageSize);

    List<PostDto> searchPostsByDescription(String description, int pageNo, int pageSize);
}
