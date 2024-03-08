package com.sosin.jussapi.api.controller;

import org.springframework.web.bind.annotation.RestController;

import com.sosin.jussapi.api.dto.response.AccountListResponseDto;
import com.sosin.jussapi.api.dto.response.AccountResponseDto;
import com.sosin.jussapi.api.dto.response.CardListResponseDto;
import com.sosin.jussapi.api.dto.response.ToPayResponseDto;
import com.sosin.jussapi.api.dto.response.TransactionListResponseDto;
import com.sosin.jussapi.api.dto.response.UsedMoneyResponseDto;
import com.sosin.jussapi.api.dto.response.UserTokenResponseDto;
import com.sosin.jussapi.api.service.ApiService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Slf4j
public class ApiController {

    private final ApiService apiService;
    
    @PostMapping("/start")    
    public UserTokenResponseDto startJuss() {

        return apiService.startJuss();
    }

    // header 받기 -> Authroization
    @GetMapping("/accounts")
    public AccountListResponseDto getAccounts(
        @RequestHeader("Authorization") String token, @RequestParam(name = "isShow", required = false) String isShow) {
        return apiService.getAccounts(token, isShow);
    }

    @GetMapping("/account/{id}")
    public AccountResponseDto getAccount(@RequestHeader("Authorization") String token, @PathVariable(name = "id") String id) {
        return apiService.getAccount(token, id);
    }
    //
    @GetMapping("/used")
    public UsedMoneyResponseDto getUsedMoney(@RequestHeader("Authorization") String token) {
        return apiService.getUsed(token);
    }

    @GetMapping("/topay")
    public ToPayResponseDto getTopay(@RequestHeader("Authorization") String token) {
        return apiService.getTopay(token);
    }

    @GetMapping("/transaction/{id}")
    public TransactionListResponseDto getTransactions(@RequestHeader("Authorization") String token, @PathVariable(name = "id") String id) {
        // isSend 여부 ===id
        // balance 잔액 계산...
        return apiService.getTransactions(token, id);
    }

    @GetMapping("/cards")
    public CardListResponseDto getCards(@RequestHeader("Authorization") String token, @RequestParam(name = "ym") String ym) {
        return apiService.getCards(token, ym);
    }
    

}
