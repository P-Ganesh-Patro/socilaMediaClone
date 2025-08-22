package com.liquibase.demo.controller;

import java.util.List;

import com.liquibase.demo.dto2.CommentDTO;
import com.liquibase.demo.response.APIResponse;
import com.liquibase.demo.services.CommentService;
import io.swagger.v3.oas.annotations.Operation;
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
@RequestMapping("/api/comments")
@Tag(name = "Post Comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Operation(summary = "post comment - create")
    @PostMapping
    public ResponseEntity<APIResponse<CommentDTO>> create(@RequestBody CommentDTO dto){
        CommentDTO comment = commentService.create(dto);
        APIResponse<CommentDTO> apiResponse=new APIResponse<>("Comment created successfully",comment);
        return new ResponseEntity<>(apiResponse,HttpStatus.CREATED);
    }


    @Operation(summary = "get all comments")
    @GetMapping
    public ResponseEntity<APIResponse<List<CommentDTO>>> getAll(){
        List<CommentDTO> comments = commentService.getAll();
        APIResponse<List<CommentDTO>> apiResponse = new APIResponse<>("All comments fetched successfully", comments);
        return new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }

    @Operation(summary = "get comment by id")
    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<CommentDTO>> getCommentById(@PathVariable Long id){
        CommentDTO comment=commentService.getCommentById(id);
        APIResponse<CommentDTO> apiResponse=new APIResponse<>("Comment found",comment);
        return new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }

    @Operation(summary = "get comment by post")
    @GetMapping("/post/{postId}")
    public ResponseEntity<APIResponse<List<CommentDTO>>> getCommentsByPost(@PathVariable Long postId){
        List<CommentDTO> comments=commentService.getCommentsByParent(postId,"POST");
        APIResponse<List<CommentDTO>> apiResponse = new APIResponse<>("All comments for post retrieved successfully", comments);
        return new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }

    @Operation(summary = "get comment by commentId")
    @GetMapping("/comment/{commentId}")
    public ResponseEntity<APIResponse<List<CommentDTO>>> getCommentsByComment(@PathVariable Long commentId){
        List<CommentDTO> comments=commentService.getCommentsByParent(commentId,"COMMENT");
        APIResponse<List<CommentDTO>> apiResponse = new APIResponse<>("All comments for comment retrieved successfully", comments);
        return new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }

    @Operation(summary = "delete comment by id")
    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse<String>> deleteComment(@PathVariable Long id){
        commentService.deleteComment(id);
        APIResponse<String> apiResponse = new APIResponse<>("Comment deleted successfully", "Deleted");
        return new ResponseEntity<>(apiResponse,HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "update comment")
    @PutMapping("/{id}")
    public ResponseEntity<APIResponse<CommentDTO>> updateComment(@PathVariable Long id,@RequestBody CommentDTO dto){
        CommentDTO comment=commentService.updateComment(id,dto);
        APIResponse<CommentDTO> apiResponse=new APIResponse<>("Comment updated successfully",comment);
        return new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }
}