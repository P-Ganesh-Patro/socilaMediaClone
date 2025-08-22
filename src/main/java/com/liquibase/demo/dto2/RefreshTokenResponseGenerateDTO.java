package com.liquibase.demo.dto2;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RefreshTokenResponseGenerateDTO {
    private String accessToken;
    private String refreshToken;
}
