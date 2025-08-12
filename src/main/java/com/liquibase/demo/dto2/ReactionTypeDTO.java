package com.liquibase.demo.dto2;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ReactionTypeDTO {
    private Long id;
    private String type;
}
