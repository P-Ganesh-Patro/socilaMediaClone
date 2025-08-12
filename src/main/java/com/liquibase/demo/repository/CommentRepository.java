package com.liquibase.demo.repository;

import com.liquibase.demo.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query(value = "SELECT * FROM comments WHERE post_id = :postId AND deleted_at IS NULL", nativeQuery = true)
    List<Comment> findByPostId(Long postId);

    @Query(value = "SELECT * FROM comments WHERE user_id = :userId AND deleted_at IS NULL", nativeQuery = true)
    List<Comment> findByUserId(Long userId);


    List<Comment> findByParentIdAndParentType(Long parentId, String parentType);

}
