package com.liquibase.demo.repository;

import com.liquibase.demo.model.GroupMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GroupMemberRepository extends JpaRepository<GroupMember, Long> {


    List<GroupMember> findByGroupId(Long groupId);


    @Query("SELECT gm FROM GroupMember gm WHERE gm.group.id = :groupId")
    List<GroupMember> findGroupMembersByGroupId(@Param("groupId") Long groupId);
}
