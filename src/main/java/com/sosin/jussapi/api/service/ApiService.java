package com.sosin.jussapi.api.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.sosin.jussapi.api.dto.request.TransferRequestDto;
import com.sosin.jussapi.api.dto.response.AccountListResponseDto;
import com.sosin.jussapi.api.dto.response.AccountResponseDto;
import com.sosin.jussapi.api.dto.response.CardListResponseDto;
import com.sosin.jussapi.api.dto.response.ResponseDataDto;
import com.sosin.jussapi.api.dto.response.ToPayResponseDto;
import com.sosin.jussapi.api.dto.response.TransactionListResponseDto;
import com.sosin.jussapi.api.dto.response.TransactionResponseDto;
import com.sosin.jussapi.api.dto.response.UsedMoneyResponseDto;
import com.sosin.jussapi.api.dto.response.UserTokenResponseDto;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ApiService {

    @Value("${server.url}")
    private String serverUrl;

    private <T> T requestApi(String url, String method, String token, Class<T> responseType) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        if (!token.equals("")) {
            headers.set("Authorization", token);
        }
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(params, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<T> response = restTemplate.exchange(
            serverUrl+url, method.equals("POST") ? HttpMethod.POST : HttpMethod.GET, 
            httpEntity, responseType);
        return response.getBody();
    }

    
    public UserTokenResponseDto startJuss() {
        UserTokenResponseDto userTokenResponseDto = requestApi("/user/", "POST", "", UserTokenResponseDto.class);

        String token = userTokenResponseDto.getTokenType() + " " + userTokenResponseDto.getAccessToken();
        ResponseDataDto<Map<String, Object>> response2 = requestApi("/data/", "POST", token, ResponseDataDto.class);

        if (!response2.getMsg().equals("data created")) {
            log.info(response2.getMsg());
            log.error("데이터 생성 실패");
        }

        return userTokenResponseDto;
    }
    

    public AccountListResponseDto getAccounts(String token, String isShow) {
        AccountListResponseDto accountList = requestApi(
            "/account/" + (isShow!=null ? "?is_show="+isShow : ""), "GET", token, AccountListResponseDto.class);
        return accountList;
    }
    public AccountResponseDto getAccount(String token, String id) {
        AccountResponseDto account = requestApi(
            "/account/"+id, "GET", token, AccountResponseDto.class);
        return account;
    }

    public AccountListResponseDto getRecent(String token, int type) {
        AccountListResponseDto accountList = requestApi(
            "/account/recent?account_type="+type, "GET", token, AccountListResponseDto.class);
        return accountList;
    }

    public UsedMoneyResponseDto getUsed(String token) {
        UsedMoneyResponseDto usedMoney = requestApi(
            "/transaction/used/", "GET", token, UsedMoneyResponseDto.class);
        return usedMoney;
    }

    public ToPayResponseDto getTopay(String token) {
        ToPayResponseDto toPay = requestApi(
            "/transaction/topay/", "GET", token, ToPayResponseDto.class);
        return toPay;
    }

    // pagination
    public TransactionListResponseDto getTransactions(String token, String id) {
        TransactionListResponseDto transactionList = requestApi(
            "/transaction/"+id, "GET", token, TransactionListResponseDto.class);
        return transactionList;
    }

    public CardListResponseDto getCards(String token, String ym) {
        CardListResponseDto cardList = requestApi(
            "/card/" + (ym != "" ? "?ym="+ym:""), "GET", token, CardListResponseDto.class);
        return cardList;
    }
    
    public TransactionResponseDto transferMoney(String token, TransferRequestDto requestDto) {
        TransactionResponseDto cardList = requestApi(
            "/transfer/", "POST", token, TransactionResponseDto.class);
        return cardList;
    }
    
    public Boolean toggleFavorite(String token, String id) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", token);

        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(params, headers);

        RestTemplate restTemplate = new RestTemplate();
        Boolean isSuccess = restTemplate.exchange(
            serverUrl+"/account/favorite/" + id, HttpMethod.PUT, 
            httpEntity, Boolean.class).getBody();

        return isSuccess;
    }
}
