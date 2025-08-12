package com.liquibase.demo.repository;

import com.liquibase.demo.model.GroupPost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroupPostRepository extends JpaRepository<GroupPost, Long> {
    List<GroupPost> findByGroupId(Long groupId);
}
