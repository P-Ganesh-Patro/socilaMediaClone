package com.liquibase.demo.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.liquibase.demo.dto2.ReactionDTO;
import com.liquibase.demo.exception.UserNotFoundException;
import com.liquibase.demo.model.Reaction;
import com.liquibase.demo.model.ReactionType;
import com.liquibase.demo.model.User;
import com.liquibase.demo.repository.*;
import org.apache.http.auth.InvalidCredentialsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ReactionService {

    @Autowired
    private ReactionRepository reactionRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private ReactionTypeRepository reactionTypeRepo;

    @Autowired
    private PostRepository postRepo;
    @Autowired
    private CommentRepository commentRepo;

    public ReactionDTO create(ReactionDTO dto) {
        User user = userRepo.findById(dto.getUserId()).orElseThrow(() -> new UserNotFoundException("User doesn't exists"));
        if (user.getDeletedAt() == null) {
            throw new UserNotFoundException("User doesn't exist");
        }
        ReactionType type = reactionTypeRepo.findById(dto.getReactionTypeId()).orElseThrow(() -> new UserNotFoundException("Reaction type not found"));
        switch (dto.getParentType().toLowerCase()) {
            case "post":
                if (!postRepo.existsById(dto.getParentId())) {
                    throw new UserNotFoundException("Post not found");
                }
                break;
            case "comment":
                if (!commentRepo.existsById(dto.getParentId())) {
                    throw new UserNotFoundException("Comment not found");
                }
                break;
            default:
                throw new IllegalArgumentException("Invalid parent type: " + dto.getParentType());
        }
        Reaction reaction = new Reaction(user, type, dto.getParentId(), dto.getParentType());
        reaction.setCreatedAt(LocalDateTime.now());
        Reaction saved = reactionRepo.save(reaction);
        return mapToDTO(saved);
    }

    private ReactionDTO mapToDTO(Reaction reaction) {
        return new ReactionDTO(
                reaction.getId(),
                reaction.getUser().getId(),
                (long) reaction.getReactionType().getId(),
                reaction.getParentId(),
                reaction.getParentType()
        );
    }

    public List<ReactionDTO> getAll() {
        return reactionRepo.findAll().stream().map(this::mapToDTO).collect((Collectors.toList()));
    }

    public ReactionDTO getReactionById(Long id) {
        Reaction reaction = reactionRepo.findById(id).orElseThrow(() -> new UserNotFoundException("Reaction not found"));
        return mapToDTO(reaction);
    }

    public List<ReactionDTO> getReactionsByParent(Long parentId, String parentType) {
        List<Reaction> reactions = reactionRepo.findByParentIdAndParentType(parentId, parentType);
        return reactions.stream().map(this::mapToDTO).collect(Collectors.toList());
    }


    public void delete(Long id) {
        Reaction reaction = reactionRepo.findById(id).orElseThrow(() -> new UserNotFoundException("Reaction not found"));
        reactionRepo.delete(reaction);
    }

    public ReactionDTO updatedReaction(Long id, ReactionDTO dto) throws InvalidCredentialsException {
        Reaction reaction = reactionRepo.findById(id).orElseThrow(() -> new UserNotFoundException("Reaction not found"));
        if (reaction.getUser().getId() != dto.getUserId()) {
            throw new InvalidCredentialsException("Same user can modify the the reaction");
        }

        ReactionType reactionType = reactionTypeRepo.findById(dto.getReactionTypeId()).orElseThrow(() -> new UserNotFoundException("Reaction type not found"));

        reaction.setReactionType(reactionType);
        reaction.setUpdatedAt(LocalDateTime.now());
        return mapToDTO(reactionRepo.save(reaction));
    }
}