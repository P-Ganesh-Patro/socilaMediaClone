package com.liquibase.demo.dto2;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UsersDTO {

    private int id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String profilePicUrl;
    private LocalDate dateOfBirth;

}
