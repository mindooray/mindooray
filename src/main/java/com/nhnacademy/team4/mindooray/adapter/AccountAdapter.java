package com.nhnacademy.team4.mindooray.adapter;

import com.nhnacademy.team4.mindooray.config.ApiProperties;
import com.nhnacademy.team4.mindooray.dto.request.CreateAccountRequest;
import com.nhnacademy.team4.mindooray.dto.response.AccountResponse;
import com.nhnacademy.team4.mindooray.dto.response.LoginResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Type;
import java.util.List;


@Service
public class AccountAdapter {
    private final RestTemplate restTemplate;
    private final ApiProperties apiProperties;
    private final String accountUrl;

    public AccountAdapter(RestTemplate restTemplate, ApiProperties apiProperties) {
        this.restTemplate = restTemplate;
        this.apiProperties = apiProperties;
        this.accountUrl = apiProperties.getAccount();
    }

    public LoginResponse getAccountByLoginId(String loginId) {
        HttpHeaders headers = getCommonHeaders();
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<LoginResponse> exchange = getExchange(
                accountUrl + "/auth/" + loginId,
                HttpMethod.GET,
                entity,
                LoginResponse.class
        );
        return exchange.getBody();
    }

    public AccountResponse register(CreateAccountRequest createAccountRequest) {
        HttpHeaders headers = getCommonHeaders();
        headers.add("Content-Type", "application/json");
        HttpEntity<CreateAccountRequest> entity = new HttpEntity<>(createAccountRequest, headers);

        ResponseEntity<AccountResponse> exchange = getExchange(
                accountUrl,
                HttpMethod.POST,
                entity,
                AccountResponse.class
        );

        return exchange.getBody();
    }

    private HttpHeaders getCommonHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", "application/json");
        return headers;
    }

    private <T> ResponseEntity<T> getExchange(String url, HttpMethod method, HttpEntity<?> entity, Class<T> clz){
        return restTemplate.exchange(url, method, entity, new ParameterizedTypeReference<>() {
            @Override
            public Type getType() {
                return clz;
            }
        });
    }
}
