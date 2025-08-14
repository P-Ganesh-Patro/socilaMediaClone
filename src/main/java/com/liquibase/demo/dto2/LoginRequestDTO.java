package com.liquibase.demo.dto2;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class LoginRequestDTO {
    @Column(nullable = false)
    private String userNameOrEmail;
    @Column(nullable = false)
    private String password;
}
