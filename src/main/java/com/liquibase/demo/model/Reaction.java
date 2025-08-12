package com.liquibase.demo.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="reactions")
@Data
public class Reaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "reaction_type_id")
    private ReactionType reactionType;

    @Column(name="parent_id")
    private Long parentId;

    @Column(name="parent_type")
    private String parentType;

    @Column(name="created_at",nullable = false)
    private LocalDateTime createdAt;

    @Column(name="updated_at")
    private LocalDateTime updatedAt;

    @Column(name="deleted_at")
    private LocalDateTime deletedAt;



    public Reaction(User user, ReactionType reactionType, Long parentId, String parentType) {
        this.user = user;
        this.reactionType = reactionType;
        this.parentId = parentId;
        this.parentType = parentType;
        this.createdAt=LocalDateTime.now();
    }



}
