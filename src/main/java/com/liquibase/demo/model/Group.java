package com.liquibase.demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;


@Entity
@Table(name = "groups")
@Data
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "created_by", nullable = false)
    @JsonBackReference("groups")
    private User createdBy;

    @Column(name = "group_name", nullable = false)
    private String groupName;

    @Column(name = "description", length = 150)
    private String description;

    private String displayName;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime created_At;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @OneToMany(mappedBy = "group")
    private List<GroupMember> members;

    @OneToMany(mappedBy = "group")
    @JsonManagedReference("group-posts")
    private List<GroupPost> groupPosts;

    public Group(User createdBy, String displayName, String description) {
        this.createdBy = createdBy;
        this.displayName = displayName;
        this.description = description;
        this.created_At=LocalDateTime.now();
    }

    @PrePersist
    public void createdAt() {
        this.created_At = LocalDateTime.now();
    }

}
