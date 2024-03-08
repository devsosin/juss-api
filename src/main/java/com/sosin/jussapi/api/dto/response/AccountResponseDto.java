package com.sosin.jussapi.api.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class AccountResponseDto {
    private int id;
    private int accountType;
    private String bankName;
    private String accountName;
    private String accountNumber;
    private long balance;
    @JsonProperty("is_show")
    private boolean isShow;
    @JsonProperty("is_favorite")
    private boolean isFavorite;
}
