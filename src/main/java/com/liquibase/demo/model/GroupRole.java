package com.liquibase.demo.model;


import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Table(name="group_roles")
@Data
public class GroupRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column( unique = true,nullable = false)
    private String role;

    private String description;

    @Column(name="created_at",nullable = false)
    private LocalDateTime createdAt;

    @Column(name="updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "groupRole",cascade = CascadeType.ALL)
    private List<GroupMember> groupMembers;


}
