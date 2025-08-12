package com.liquibase.demo.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.liquibase.demo.dto2.GroupRoleDTO;
import com.liquibase.demo.exception.UserNotFoundException;
import com.liquibase.demo.model.GroupRole;
import com.liquibase.demo.repository.GroupRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class GroupRoleService {
    @Autowired
    private GroupRoleRepository groupRoleRepo;

    public GroupRoleDTO createGroupRole(GroupRoleDTO dto) {
        if(groupRoleRepo.existsByRole(dto.getRole())){
            throw new RuntimeException("Group role already exists");
        }
        GroupRole role=new GroupRole();
        role.setRole(dto.getRole());
        role.setDescription(dto.getDescription());
        role.setCreatedAt(LocalDateTime.now());

        GroupRole saved=groupRoleRepo.save(role);
        return mapToDTO(saved);
    }

    private GroupRoleDTO mapToDTO(GroupRole role) {
        return new GroupRoleDTO(
                role.getId(),
                role.getRole(),
                role.getDescription()
        );
    }

    public List<GroupRoleDTO> getAll() {
        return groupRoleRepo.findAll().stream()
                .map(role->new GroupRoleDTO(role.getId(),role.getRole(),role.getDescription()))
                .collect(Collectors.toList());
    }

    public GroupRoleDTO getById(Long id) {
        GroupRole role=groupRoleRepo.findById(id).orElseThrow(()->new UserNotFoundException("Group role not found"));
        return mapToDTO(role);
    }

    public GroupRoleDTO updateGroupRole(Long id, GroupRoleDTO dto) {
        GroupRole role=groupRoleRepo.findById(id).orElseThrow(()->new UserNotFoundException("Group role not found"));
        role.setRole(dto.getRole());
        role.setDescription(dto.getDescription());
        role.setUpdatedAt(LocalDateTime.now());
        GroupRole updated=groupRoleRepo.save(role);
        return mapToDTO(updated);
    }

    public void deleteById(Long id) {
        GroupRole role=groupRoleRepo.findById(id).orElseThrow(()->new UserNotFoundException("Group role not found"));
        groupRoleRepo.delete(role);
    }
}