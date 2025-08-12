package com.liquibase.demo.repository;

import com.liquibase.demo.model.Reaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReactionRepository extends JpaRepository<Reaction, Long> {

    //    List<Reaction> findByCommentId(Long commentId);
    List<Reaction> findByParentIdAndParentType(Long parentId, String parentType);


}
