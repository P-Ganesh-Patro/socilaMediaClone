package com.liquibase.demo.dto2;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChangePasswordResponseDTO {
    private Long id;
    private String message;
}
