package com.liquibase.demo.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.liquibase.demo.dto2.GroupDTO;
import com.liquibase.demo.exception.UserNotFoundException;
import com.liquibase.demo.model.*;
import com.liquibase.demo.repository.GroupMemberRepository;
import com.liquibase.demo.repository.GroupPostRepository;
import com.liquibase.demo.repository.GroupRepository;
import com.liquibase.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class GroupService {
    @Autowired
    private GroupRepository groupRepo;
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private GroupPostRepository groupPostRepo;
    @Autowired
    private GroupMemberRepository groupMemberRepo;

    public GroupDTO createGroup(GroupDTO dto) {
        User creator=userRepo.findById(dto.getCreatedBy()).orElseThrow(()->new UserNotFoundException("User not found"));
        Group group = new Group(creator, dto.getDisplayName(), dto.getDescription());
        group.setCreated_At(LocalDateTime.now());

        return mapToDTO(groupRepo.save(group));
    }

    private GroupDTO mapToDTO(Group group) {
        return new GroupDTO(group.getId(),group.getCreatedBy().getId(),group.getDisplayName(),group.getDescription());
    }

    public List<GroupDTO> getAll() {
        return groupRepo.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public GroupDTO getById(Long id) {
        Group group=groupRepo.findById(id).orElseThrow(()->new UserNotFoundException("group not found"));
        return mapToDTO(group);
    }

    public List<Post> getAllPostsInGroup(Long id) {
        List<GroupPost> posts=groupPostRepo.findByGroupId(id);
        return posts.stream().map(post->post.getPost()).collect(Collectors.toList());
    }

    public List<String> getAllUsersInGroup(Long groupId) {
        List<GroupMember> members = groupMemberRepo.findGroupMembersByGroupId(groupId);
        return members.stream().map(gm -> gm.getUser().getUsername()).collect(Collectors.toList());
    }

    public void deleteGroup(Long id) {
        Group group=groupRepo.findById(id).orElseThrow(()->new UserNotFoundException("Group not found"));
        groupRepo.delete(group);
    }

    public GroupDTO updateGroup(Long id, GroupDTO dto) {
        Group group=groupRepo.findById(id).orElseThrow(()->new UserNotFoundException("Group not found"));
        group.setDisplayName(dto.getDisplayName());
        group.setDescription(dto.getDescription());
        group.setUpdatedAt(LocalDateTime.now());
        return mapToDTO(group);
    }
}
