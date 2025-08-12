package com.liquibase.demo.repository;

import com.liquibase.demo.model.ReactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReactionTypeRepository extends JpaRepository<ReactionType, Long> {
//    Optional<ReactionType> findByReactionType(Enum reactionType);


//    boolean findByType(String type);

    boolean existsByType(String type);


}
