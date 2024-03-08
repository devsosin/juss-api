package com.sosin.jussapi.api.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CardResponseDto {
    private int id;
    private int minUsage;
    private String cardName;
    private int amount;
}
