package com.app.blogger.controller;

import com.app.blogger.payload.PostDto;
import com.app.blogger.payload.PostResponse;
import com.app.blogger.service.PostService;
import com.app.blogger.utils.AppConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
@Tag(
        name = "CRUD REST APIs for Post Resource"
)
public class PostController {

    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    // Create a post
    @Operation(
            summary = "Create Post REST API",
            description = "Create Post REST API is used to save post into database"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Http Status 201 CREATED"
    )
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto) {
        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }

    // Get all posts
    @Operation(
            summary = "Get All Posts REST API",
            description = "Get All Posts REST API is used to fetch all the posts from database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESS"
    )
    @GetMapping
    public PostResponse getAllPosts(
            @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ) {
        return postService.getAllPosts(pageNo, pageSize, sortBy, sortDir);
    }

    // Get post by id
    @Operation(
            summary = "Get Post By Id REST API",
            description = "Get Post By Id REST API is used to get a post from database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESS"
    )
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Long id) {
        return ResponseEntity.ok(postService.getPostById(id));
    }

    // Get post by category
    @Operation(
            summary = "Get Post By Category Id REST API",
            description = "Get Post By Category Id REST API is used to get a post from database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESS"
    )
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<PostDto>> getPostByCategory(@PathVariable Long categoryId) {
        List<PostDto> postDtoList = postService.getPostByCategory(categoryId);
        return ResponseEntity.ok(postDtoList);
    }

    // Update a post
    @Operation(
            summary = "Update Post REST API",
            description = "Update Post REST API is used to update a Post in the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESS"
    )
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto, @PathVariable Long id) {
        PostDto updatePostDto = postService.updatePost(postDto, id);
        return new ResponseEntity<>(updatePostDto, HttpStatus.OK);
    }

    // Delete a post
    @Operation(
            summary = "Delete Post REST API",
            description = "Delete Post REST API is used to delete a Post from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESS"
    )
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return new ResponseEntity<>("Post deleted successfully !", HttpStatus.OK);
    }

    // Search posts by title
    @Operation(
            summary = "Search Post By Title REST API",
            description = "Search Post By Title REST API is used to get a post from database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESS"
    )
    @GetMapping("/title/{title}")
    public ResponseEntity<List<PostDto>> searchPostsByTitle(@PathVariable String title) {
        List<PostDto> postDtoList = postService.searchPostsByTitle(title);
        return ResponseEntity.ok(postDtoList);
    }

    // Search posts by description
    @Operation(
            summary = "Search Post By Description REST API",
            description = "Search Post By Description REST API is used to get a post from database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESS"
    )
    @GetMapping("/description/{description}")
    public ResponseEntity<List<PostDto>> searchPostsByDescription(@PathVariable String description) {
        List<PostDto> postDtoList = postService.searchPostsByDescription(description);
        return ResponseEntity.ok(postDtoList);
    }
}
