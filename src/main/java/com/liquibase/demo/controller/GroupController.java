package com.liquibase.demo.controller;

import java.util.List;

import com.liquibase.demo.dto2.GroupDTO;
import com.liquibase.demo.model.Post;
import com.liquibase.demo.response.APIResponse;
import com.liquibase.demo.services.GroupService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/api/groups")
@Tag(name = "Group")
public class GroupController {
    @Autowired
    private GroupService groupService;

    @PostMapping
    public ResponseEntity<APIResponse<GroupDTO>> createGroup(@RequestBody GroupDTO dto){
        GroupDTO created = groupService.createGroup(dto);
        APIResponse<GroupDTO> apiResponse=new APIResponse<>("Group created successfully",created);
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<APIResponse<List<GroupDTO>>> getAll(){
        List<GroupDTO> list = groupService.getAll();
        APIResponse<List<GroupDTO>> apiResponse=new APIResponse<>("All groups Fetched successfully",list);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<GroupDTO>> getById(@PathVariable Long id){
        GroupDTO dto = groupService.getById(id);
        APIResponse<GroupDTO> apiResponse=new APIResponse<>("Group found",dto);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
    @GetMapping("/{id}/posts")
    public ResponseEntity<APIResponse<List<Post>>> getAllPostsInGroup(@PathVariable Long id){
        List<Post> posts = groupService.getAllPostsInGroup(id);
        APIResponse<List<Post>> apiResponse=new APIResponse<>("Fetched posts in group",posts);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
    @GetMapping("/{id}/users")
    public ResponseEntity<APIResponse<List<String>>> getAllUsersInGroup(@PathVariable Long id){
        List<String> users = groupService.getAllUsersInGroup(id);
        APIResponse<List<String>> apiResponse=new APIResponse<>("Fetched members in group",users);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse<String>> deleteGroup(@PathVariable Long id){
        groupService.deleteGroup(id);
        APIResponse<String> apiResponse=new APIResponse<>("Group deleted successfully","Deleted");
        return new ResponseEntity<>(apiResponse,HttpStatus.NO_CONTENT);
    }
    @PutMapping("/{id}")
    public ResponseEntity<APIResponse<GroupDTO>> updateGroup(@PathVariable Long id, @RequestBody GroupDTO dto){
        GroupDTO updated = groupService.updateGroup(id, dto);
        APIResponse<GroupDTO> apiResponse=new APIResponse<>("Group updates successfully",updated);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

}
