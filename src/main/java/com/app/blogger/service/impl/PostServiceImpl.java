package com.app.blogger.service.impl;

import com.app.blogger.exception.BlogAPIException;
import com.app.blogger.exception.ResourceNotFoundException;
import com.app.blogger.model.Post;
import com.app.blogger.model.User;
import com.app.blogger.payload.PostDto;
import com.app.blogger.payload.PostResponse;
import com.app.blogger.repository.PostRepository;
import com.app.blogger.service.PostService;
import com.app.blogger.service.UserProxyService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;
    private ModelMapper modelMapper;

    @Autowired
    private UserProxyService userProxyService;

    public PostServiceImpl(PostRepository postRepository, ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Post createPost(PostDto postDto) {
        ResponseEntity<User> user = userProxyService.findUser(String.valueOf(postDto.getId()), "id");
        if (user.getStatusCode().isError() || user.getBody() == null) {
            throw new BlogAPIException(HttpStatus.resolve(user.getStatusCode().value()), "Error");
        }
        Post post = toEntity(postDto);
        post.setUser(user.getBody());
        return postRepository.save(post);
    }

    @Override
    public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {

        Sort sort = Sort.Direction.ASC.name().equalsIgnoreCase(sortDir) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Post> posts = postRepository.findAll(pageable);

        List<Post> postList = posts.getContent();

        List<PostDto> content = postList.stream().map(post -> toDto(post)).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(content);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setLast(posts.isLast());

        return postResponse;
    }

    @Override
    public Post getPostById(Long id) {
        return postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
    }

    @Override
    public Post updatePost(PostDto postDto, Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));

        ResponseEntity<User> user = userProxyService.findUser(String.valueOf(postDto.getId()), "id");
        if (user.getStatusCode().isError() || user.getBody() == null) {
            throw new BlogAPIException(HttpStatus.resolve(user.getStatusCode().value()), "Error");
        }
        if (!user.getBody().getId().equals(post.getId())) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Incorrect user is updating blog.");
        }

        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        return postRepository.save(post);
    }

    @Override
    public void deletePost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        postRepository.delete(post);
    }


    // Dto to entity
    private Post toEntity(PostDto postDto) {
//        Post post = new Post();
//        post.setTitle(postDto.getTitle());
//        post.setDescription(postDto.getDescription());
//        post.setContent(postDto.getContent());
        return modelMapper.map(postDto, Post.class);
    }

    // Entity to dto
    private PostDto toDto(Post post) {
//        PostDto postDto = new PostDto();
//        postDto.setId(post.getId());
//        postDto.setTitle(post.getTitle());
//        postDto.setDescription(post.getDescription());
//        postDto.setContent(post.getContent());
        return modelMapper.map(post, PostDto.class);
    }

}
