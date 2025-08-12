package com.liquibase.demo.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.liquibase.demo.dto2.GroupMemberDTO;
import com.liquibase.demo.exception.UserNotFoundException;
import com.liquibase.demo.model.Group;
import com.liquibase.demo.model.GroupMember;
import com.liquibase.demo.model.GroupRole;
import com.liquibase.demo.model.User;
import com.liquibase.demo.repository.GroupMemberRepository;
import com.liquibase.demo.repository.GroupRepository;
import com.liquibase.demo.repository.GroupRoleRepository;
import com.liquibase.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class GroupMemberService {

    @Autowired
    private GroupMemberRepository groupMemberRepo;

    @Autowired
    private GroupRepository groupRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private GroupRoleRepository groupRoleRepo;

    public GroupMemberDTO addMemberToGroup(Long groupId, GroupMemberDTO dto) {
        Group group = groupRepo.findById(groupId).orElseThrow(() -> new UserNotFoundException("Group not found with id: " + groupId));
        User user = userRepo.findById(dto.getUserId()).orElseThrow(() -> new UserNotFoundException("User not found with id: " + dto.getUserId()));
        GroupRole groupRole = groupRoleRepo.findById(dto.getRoleId()).orElseThrow(() -> new UserNotFoundException("Role not found with id: " + dto.getRoleId()));

        GroupMember member = new GroupMember();
        member.setGroup(group);
        member.setUser(user);
        member.setGroupRole(groupRole);
        member.setCreatedAt(LocalDateTime.now());

        return mapToDTO(groupMemberRepo.save(member));
    }

    private GroupMemberDTO mapToDTO(GroupMember member) {
        return new GroupMemberDTO(
                member.getId(),
                member.getGroup().getId(),
                member.getUser().getId(),
                member.getGroupRole().getId()
        );
    }

    public List<GroupMemberDTO> getAll() {
        return groupMemberRepo.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public GroupMemberDTO getById(Long id) {
        GroupMember member = groupMemberRepo.findById(id).orElseThrow(() -> new UserNotFoundException("Group member not found with id: " + id));
        return mapToDTO(member);
    }

    public GroupMemberDTO update(Long id, GroupMemberDTO dto) {
        GroupMember member = groupMemberRepo.findById(id).orElseThrow(() -> new UserNotFoundException("Group member not found with id: " + id));
        GroupRole role = groupRoleRepo.findById(dto.getRoleId()).orElseThrow(() -> new UserNotFoundException("Role not found with id: " + dto.getRoleId()));
        member.setGroupRole(role);
        member.setUpdatedAt(LocalDateTime.now());

        return mapToDTO(groupMemberRepo.save(member));
    }

    public void delete(Long id) {
        GroupMember member = groupMemberRepo.findById(id).orElseThrow(() -> new UserNotFoundException("Group member not found with id: " + id));
        groupMemberRepo.delete(member);
    }
}
