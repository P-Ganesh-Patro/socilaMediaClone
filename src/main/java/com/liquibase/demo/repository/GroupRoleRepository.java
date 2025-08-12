package com.liquibase.demo.repository;

import com.liquibase.demo.model.GroupRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRoleRepository extends JpaRepository<GroupRole, Long> {
    boolean existsByRole(String role);

}
