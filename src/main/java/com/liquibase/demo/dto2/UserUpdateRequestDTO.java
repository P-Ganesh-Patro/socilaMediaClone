package com.liquibase.demo.dto2;

import lombok.Data;

@Data
public class UserUpdateRequestDTO {
    private String userName;
    private String firstName;
    private String lastName;
    private String email;
    private String dateOfBirth;

}
