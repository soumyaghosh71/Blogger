package com.app.blogger.service;

import com.app.blogger.model.Post;
import com.app.blogger.payload.PostDto;
import com.app.blogger.payload.PostResponse;

public interface PostService {
    Post createPost(PostDto postDto);

    PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);

    Post getPostById(Long id);

    Post updatePost(PostDto postDto, Long id);

    void deletePost(Long id);
}
