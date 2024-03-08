package com.sosin.jussapi.api.service;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.sosin.jussapi.api.dto.response.AccountListResponseDto;
import com.sosin.jussapi.api.dto.response.AccountResponseDto;
import com.sosin.jussapi.api.dto.response.CardListResponseDto;
import com.sosin.jussapi.api.dto.response.ResponseDataDto;
import com.sosin.jussapi.api.dto.response.ToPayResponseDto;
import com.sosin.jussapi.api.dto.response.TransactionListResponseDto;
import com.sosin.jussapi.api.dto.response.UsedMoneyResponseDto;
import com.sosin.jussapi.api.dto.response.UserTokenResponseDto;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ApiService {
    
    public UserTokenResponseDto startJuss() {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(params, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<UserTokenResponseDto> response = restTemplate.exchange(
            "http://localhost:8000/api/v1/user/", HttpMethod.POST, httpEntity, UserTokenResponseDto.class);
        UserTokenResponseDto userTokenResponseDto = response.getBody();

        RestTemplate restTemplate2 = new RestTemplate();

        HttpHeaders headers2 = new HttpHeaders();
        headers2.setContentType(MediaType.APPLICATION_JSON);
        headers2.set("Authorization", userTokenResponseDto.getTokenType() + " " + userTokenResponseDto.getAccessToken());
        HttpEntity<MultiValueMap<String, String>> httpEntity2 = new HttpEntity<>(params, headers2);

        ResponseDataDto<Map<String, Object>> response2 = restTemplate2.exchange(
                    "http://localhost:8000/api/v1/data/", 
                    HttpMethod.POST, httpEntity2,
                    new ParameterizedTypeReference<ResponseDataDto<Map<String, Object>>>() {}).getBody();

        if (!response2.getMsg().equals("data created")) {
            log.info(response2.getMsg());
            log.error("데이터 생성 실패");
        }

        return userTokenResponseDto;
    }
    

    public AccountListResponseDto getAccounts(String token, String isShow) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", token);

        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(params, headers);

        RestTemplate restTemplate = new RestTemplate();
        log.info("http://localhost:8000/api/v1/account/?is_show=" + (isShow!=null ? isShow : ""));
        ResponseEntity<AccountListResponseDto> response = restTemplate.exchange(
            "http://localhost:8000/api/v1/account/" + (isShow!=null ? "?is_show="+isShow : ""), HttpMethod.GET, httpEntity, AccountListResponseDto.class);
        
        return response.getBody();
    }
    public AccountResponseDto getAccount(String token, String id) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", token);

        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(params, headers);

        RestTemplate restTemplate = new RestTemplate();
        log.info("http://localhost:8000/api/v1/account/"+id);
        ResponseEntity<AccountResponseDto> response = restTemplate.exchange(
            "http://localhost:8000/api/v1/account/"+id, HttpMethod.GET, httpEntity, AccountResponseDto.class);
        
        return response.getBody();
    }

    public UsedMoneyResponseDto getUsed(String token) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", token);

        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(params, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<UsedMoneyResponseDto> response = restTemplate.exchange(
            "http://localhost:8000/api/v1/transaction/used/", HttpMethod.GET, httpEntity, UsedMoneyResponseDto.class);
        
        return response.getBody();
    }

    public ToPayResponseDto getTopay(String token) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", token);

        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(params, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<ToPayResponseDto> response = restTemplate.exchange(
            "http://localhost:8000/api/v1/transaction/topay/", HttpMethod.GET, httpEntity, ToPayResponseDto.class);
        
        return response.getBody();
    }

    // pagination
    public TransactionListResponseDto getTransactions(String token, String id) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", token);

        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(params, headers);

        RestTemplate restTemplate = new RestTemplate();
        log.info("http://localhost:8000/api/v1/transaction/"+id);
        ResponseEntity<TransactionListResponseDto> response = restTemplate.exchange(
            "http://localhost:8000/api/v1/transaction/"+id, HttpMethod.GET, httpEntity, TransactionListResponseDto.class);
        
        return response.getBody();
    }

    public CardListResponseDto getCards(String token, String ym) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", token);

        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(params, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<CardListResponseDto> response = restTemplate.exchange(
            "http://localhost:8000/api/v1/card/" + (ym != "" ? "?ym="+ym:""), HttpMethod.GET, httpEntity, CardListResponseDto.class);
        
        return response.getBody();
    }
}
