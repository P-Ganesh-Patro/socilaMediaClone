package com.liquibase.demo.model;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, unique=true)
    private String username;

    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    @Column(nullable = false,unique = true)
    @Email(message = "Invalid email format")
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(name="profile_pic_url")
    private String profilePicUrl;

    @Column(name="created_at",nullable = false)
    private LocalDateTime createdAt;

    @Column(name="updated_at")
    private LocalDateTime updatedAt;

    @Column(name="deleted_at")
    private LocalDateTime deletedAt;

    @Column(name="date_of_birth")
    private LocalDate dateOfBirth;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Post> posts;

    @OneToMany(mappedBy = "user",cascade=CascadeType.ALL)
    private List<Comment> comments;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<Reaction> reactions;

    @OneToMany(mappedBy = "createdBy",cascade = CascadeType.ALL)
    private List<Group> groups;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<GroupMember> groupMembers;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    @JsonManagedReference("group-posts")
    private List<GroupPost> groupPosts;


    @PrePersist
    public void createDate(){
        this.createdAt = LocalDateTime.now();
    }
}
