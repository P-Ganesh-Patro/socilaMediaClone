package com.liquibase.demo.dto2;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReactionDTO {
    private Long id;
    private Long userId;
    private Long reactionTypeId;
    private Long parentId;
    private String parentType;
}
