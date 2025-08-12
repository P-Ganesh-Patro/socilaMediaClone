package com.liquibase.demo.controller;

import java.util.List;

import com.liquibase.demo.dto2.PostDTO;
import com.liquibase.demo.response.APIResponse;
import com.liquibase.demo.services.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;



import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/posts")
@Tag(name = "Post Files")
public class PostController {
    @Autowired
    private PostService postService;


    @Operation(summary = "create post")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<APIResponse<PostDTO>> createPost(@RequestParam("userId") Long userId, @RequestParam("content") String content, @RequestParam("media") List<MultipartFile> media){
        PostDTO created = postService.createPost(userId,content,media);
        APIResponse<PostDTO> apiResponse=new APIResponse<>("Post created successfully",created);
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @Operation(summary = "get all post")
    @GetMapping
    public ResponseEntity<APIResponse<List<PostDTO>>> getAll(){
        List<PostDTO> posts = postService.getAll();
        APIResponse<List<PostDTO>> apiResponse=new APIResponse<>("Retrived all posts",posts);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @Operation(summary = "get by id")
    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<PostDTO>> getById(@PathVariable Long id){
        PostDTO post = postService.getById(id);
        APIResponse<PostDTO> apiResponse=new APIResponse<>("Fetched post",post);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @Operation(summary = "get post by userId")
    @GetMapping("/user/{userId}")
    public ResponseEntity<APIResponse<List<PostDTO>>> getPostsByUser(@PathVariable Long userId){
        List<PostDTO> posts = postService.getPostsByUser(userId);
        APIResponse<List<PostDTO>> apiResponse=new APIResponse<>("Fetched all posts of user",posts);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @Operation(summary = "delete post by id")
    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse<String>> deletePost(@PathVariable Long id){
        postService.deletePost(id);
        APIResponse<String> apiResponse=new APIResponse<>("Deleted post","Delted");
        return new ResponseEntity<>(apiResponse,HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "update post by id")
    @PutMapping("/{id}")
    public ResponseEntity<APIResponse<PostDTO>> updatePost(@PathVariable Long id,@RequestBody PostDTO dto){
        PostDTO updated = postService.updatePost(id, dto);
        APIResponse<PostDTO> apiResponse=new APIResponse<>("Updated post successfully",updated);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }


}