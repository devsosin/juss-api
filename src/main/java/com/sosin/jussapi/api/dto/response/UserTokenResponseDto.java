package com.sosin.jussapi.api.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UserTokenResponseDto {
    private String accessToken;
    private String refreshToken;
    private String tokenType;
}
