package com.liquibase.demo.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.liquibase.demo.dto2.CommentDTO;
import com.liquibase.demo.exception.UserNotFoundException;
import com.liquibase.demo.model.Comment;
import com.liquibase.demo.model.User;
import com.liquibase.demo.repository.CommentRepository;
import com.liquibase.demo.repository.PostRepository;
import com.liquibase.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepo;

    @Autowired
    private UserRepository userRepo;
    @Autowired
    private PostRepository postRepo;


    public CommentDTO create(CommentDTO dto) {
        User user = userRepo.findById(dto.getUserId()).orElseThrow(() -> new UserNotFoundException("User not exists"));
        Comment comment = new Comment();
        comment.setUser(user);
        comment.setParentId((dto.getParentId()));
        comment.setParentType(dto.getParentType());
        comment.setComment(dto.getComment());
        comment.setCreatedAt(LocalDateTime.now());

        return mapToDTO(commentRepo.save(comment));
    }

    private CommentDTO mapToDTO(Comment comment) {
        return new CommentDTO(
                comment.getId(),
                (long) comment.getUser().getId(),
                comment.getParentId(),
                comment.getParentType(),
                comment.getComment()
        );
    }

    public List<CommentDTO> getAll() {
        return commentRepo.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public CommentDTO getCommentById(Long id) {
        Comment comment = commentRepo.findById(id).orElseThrow(() -> new UserNotFoundException("comment not found"));
        return mapToDTO(comment);
    }

    public List<CommentDTO> getCommentsByParent(Long parentId, String parentType) {
        return commentRepo.findByParentIdAndParentType(parentId, parentType).stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public void deleteComment(Long id) {
        Comment comment = commentRepo.findById(id).orElseThrow(() -> new UserNotFoundException("Comment not found"));
        if(comment!=null){
            comment.setDeletedAt(LocalDateTime.now());
        }

        commentRepo.save(comment);
//        commentRepo.delete(comment);
    }

    public CommentDTO updateComment(Long id, CommentDTO dto) {
        Comment comment = commentRepo.findById(id).orElseThrow(() -> new UserNotFoundException("Comment not found"));
        comment.setComment(dto.getComment());
        comment.setUpdatedAt(LocalDateTime.now());
        Comment updated = commentRepo.save(comment);
        return mapToDTO(updated);
    }


}