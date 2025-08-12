package com.liquibase.demo.controller;

import java.util.List;

import com.liquibase.demo.dto2.ReactionDTO;
import com.liquibase.demo.response.APIResponse;
import com.liquibase.demo.services.ReactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.http.auth.InvalidCredentialsException;
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
@RequestMapping("/api/reactions")
@Tag(name = "Reaction on Post")
public class ReactionController {

    @Autowired
    private ReactionService reactionService;

    @Operation(summary = "reaction create")
    @PostMapping
    public ResponseEntity<APIResponse<ReactionDTO>> create(@RequestBody ReactionDTO dto) {
        ReactionDTO reaction = reactionService.create(dto);
        APIResponse<ReactionDTO> apiResponse = new APIResponse<>("Reaction created successfully", reaction);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @Operation(summary = "Get all reactions")
    @GetMapping
    public ResponseEntity<APIResponse<List<ReactionDTO>>> getAll() {
        List<ReactionDTO> reactions = reactionService.getAll();
        APIResponse<List<ReactionDTO>> apiResponse = new APIResponse<>("fetched all reactions", reactions);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @Operation(summary = "get reactions by Id")
    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<ReactionDTO>> getReactionById(@PathVariable Long id) {
        ReactionDTO reaction = reactionService.getReactionById(id);
        APIResponse<ReactionDTO> apiResponse = new APIResponse<>("fetched reaction", reaction);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @Operation(summary = "get reaction by post")
    @GetMapping("/post/{postId}")
    public ResponseEntity<APIResponse<List<ReactionDTO>>> getReactionsByPost(@PathVariable Long postId) {
        List<ReactionDTO> reactions = reactionService.getReactionsByParent(postId, "POST");
        APIResponse<List<ReactionDTO>> apiResponse = new APIResponse<>("fetched reactions of post", reactions);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }


    @Operation(summary = "get reaction by comment")
    @GetMapping("/comment/{commentId}")
    public ResponseEntity<APIResponse<List<ReactionDTO>>> getReactionByComment(@PathVariable Long commentId) {
        List<ReactionDTO> reactions = reactionService.getReactionsByParent(commentId, "COMMENT");
        APIResponse<List<ReactionDTO>> apiResponse = new APIResponse<>("fetched reactions of comment", reactions);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @Operation(summary = "delete reaction")
    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse<String>> deleteReaction(@PathVariable Long id) {
        reactionService.delete(id);
        APIResponse<String> apiResponse = new APIResponse<>("Reaction deleted", "Deleted");
        return new ResponseEntity<>(apiResponse, HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "update reaction")
    @PutMapping("{id}")
    public ResponseEntity<APIResponse<ReactionDTO>> updatedReaction(@PathVariable Long id, @RequestBody ReactionDTO dto) throws InvalidCredentialsException {
        ReactionDTO updated = reactionService.updatedReaction(id, dto);
        APIResponse<ReactionDTO> apiResponse = new APIResponse<>("Updated reaction", updated);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
