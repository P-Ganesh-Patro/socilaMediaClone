package com.liquibase.demo.controller;

import java.util.List;

import com.liquibase.demo.dto2.GroupMemberDTO;
import com.liquibase.demo.response.APIResponse;
import com.liquibase.demo.services.GroupMemberService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/group-members")
@Tag(name = "Group Members")
public class GroupMemberController {

    @Autowired
    private GroupMemberService groupMemberService;

    @PostMapping("/group/{groupId}")
    public ResponseEntity<APIResponse<GroupMemberDTO>> addMemberToGroup(@PathVariable Long groupId,@RequestBody GroupMemberDTO dto) {
        GroupMemberDTO created = groupMemberService.addMemberToGroup(groupId, dto);
        APIResponse<GroupMemberDTO> apiResponse=new APIResponse<>("Group member created",created);
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<APIResponse<List<GroupMemberDTO>>> getAll() {
        List<GroupMemberDTO> list = groupMemberService.getAll();
        APIResponse<List<GroupMemberDTO>> apiResponse=new APIResponse<>("fetched all group members",list);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<GroupMemberDTO>> getById(@PathVariable Long id) {
        GroupMemberDTO dto = groupMemberService.getById(id);
        APIResponse<GroupMemberDTO> apiResponse=new APIResponse<>("fetched group member",dto);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<APIResponse<GroupMemberDTO>> update(@PathVariable Long id, @RequestBody GroupMemberDTO dto) {
        GroupMemberDTO updated = groupMemberService.update(id, dto);
        APIResponse<GroupMemberDTO> apiResponse=new APIResponse<>("Group member updated",updated);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse<String>> delete(@PathVariable Long id) {
        groupMemberService.delete(id);
        APIResponse<String> apiResponse=new APIResponse<>("Group member deleted successfully","Deleted");
        return new ResponseEntity<>(apiResponse,HttpStatus.NO_CONTENT);
    }
}
