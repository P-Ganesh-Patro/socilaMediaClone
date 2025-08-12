package com.liquibase.demo.model;


import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="posts_media")
@Data
public class PostMedia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name ="post_id",nullable = false)
    private Post post;

    @Column(name="media_url")
    private String mediaUrl;

    @Column(name="media_type")
    private String mediaType;

    @Column(name="created_at",nullable = false)
    private LocalDateTime createdAt;

    @Column(name="updated_at")
    private LocalDateTime updatedAt;

    public PostMedia() {
    }

    public PostMedia(Post post, String mediaUrl, String mediaType) {
        this.post = post;
        this.mediaUrl = mediaUrl;
        this.mediaType = mediaType;
        this.createdAt=LocalDateTime.now();
    }

   

}
