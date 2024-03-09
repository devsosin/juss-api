package com.sosin.jussapi.api.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TransferRequestDto {
    private int senderId;
    private int receiverId;
    private int amount;
    private String memo;
}
