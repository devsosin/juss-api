package com.sosin.jussapi.api.dto.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TransactionResponseDto {
    private int id;
    private int amount;
    private String memo;
    @JsonProperty("is_fill")
    private boolean isFill;
    private int sender_id;

    // receiver
    // sender

    // 
    private LocalDateTime createdAt;
}
