package com.liquibase.demo.repository;

import com.liquibase.demo.model.PostMedia;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostMediaRepository extends JpaRepository<PostMedia, Long> {
}
