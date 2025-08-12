package com.liquibase.demo.dto2;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupRoleDTO {
    private Long id;
    private String role;
    private String description;
}
