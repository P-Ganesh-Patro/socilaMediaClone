package com.liquibase.demo.dto2;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class SignUpResponseDTO {
    private Long id;
    private String userName;
    private String firstName;
    private String lastName;
    private String email;
    private LocalDateTime createdAt;
    private LocalDate dateOfBirth;
}
