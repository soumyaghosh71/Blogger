package com.app.blogger.repository;

import com.app.blogger.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByCategoryId(Long categoryId);

    // Using JPQL
    @Query("SELECT p FROM Post p WHERE " +
            "p.title LIKE CONCAT('%', :title, '%')")
    Page<Post> searchPostsByTitle(String title, Pageable pageable);

    // Using Native SQL
    @Query(value = "SELECT * FROM posts p WHERE " +
            "p.description LIKE CONCAT('%', :description, '%')", nativeQuery = true)
    Page<Post> searchPostsByDescription(String description, Pageable pageable);
}
