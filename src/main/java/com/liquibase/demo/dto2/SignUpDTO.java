package com.liquibase.demo.dto2;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpDTO {
    private Long id;
    private String userName;
    private String firstName;
    private String lastName;
    private String email;
    private LocalDate dob;
}