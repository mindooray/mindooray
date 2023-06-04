package com.nhnacademy.team4.mindooray.adapter;

import com.nhnacademy.team4.mindooray.config.ApiProperties;
import com.nhnacademy.team4.mindooray.dto.request.CreateAccountRequest;
import com.nhnacademy.team4.mindooray.dto.response.AccountResponse;
import com.nhnacademy.team4.mindooray.dto.response.LoginResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Type;


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

    /**
     * loginId(사용자 로그인 아이디)를 통해 사용자 정보 요청
     *
     * @param loginId 사용자 로그인 아이디
     * @return 매팽된 {@link LoginResponse} 객체
     */
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

    /**
     * 사용자 이메일을 통해 사용자 정보 요청
     *
     * @param email 사용자 이메일
     * @return 매핑된 {@link LoginResponse} 객체
     */

    public LoginResponse getAccountByEmail(String email) {
        HttpHeaders headers = getCommonHeaders();
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<LoginResponse> exchange = getExchange(
                accountUrl + "/auth/login/{email}",
                HttpMethod.GET,
                entity,
                LoginResponse.class,
                email
        );

        return exchange.getBody();
    }

    /**
     * account 생성 요청
     *
     * @param createAccountRequest
     * @return 매핑된 {@link AccountResponse} 객체
     */

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

    /**
     * 공통 헤더 메서드
     * - Accept: application/json
     *
     * @return
     */
    private HttpHeaders getCommonHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", "application/json");
        return headers;
    }

    /**
     *
     * @param url       요청 url
     * @param method    요청 method
     * @param entity    요청 header, body
     * @param clz       응답에 매핑될 class 타입
     * @return          요청에 대한 응답 ResponseEntity 객체
     * @param <T>       응답에 매핑될 class
     */
    private <T> ResponseEntity<T> getExchange(String url, HttpMethod method, HttpEntity<?> entity, Class<T> clz) {
        return restTemplate.exchange(url, method, entity, new ParameterizedTypeReference<>() {
            @Override
            public Type getType() {
                return clz;
            }
        });
    }

    /**
     *
     * @param url               요청 url
     * @param method            요청 method
     * @param entity            요청 header, body
     * @param clz               응답에 매핑될 class 타입
     * @param pathVariables     요청 url 에 사용되는 경로 변수
     * @return
     * @param <T>
     */
    private <T> ResponseEntity<T> getExchange(String url, HttpMethod method, HttpEntity<?> entity, Class<T> clz, Object... pathVariables) {
        return restTemplate.exchange(url, method, entity,
                new ParameterizedTypeReference<T>() {
                    @Override
                    public Type getType() {
                        return clz;
                    }
                },
                (Object) pathVariables);
    }
}
