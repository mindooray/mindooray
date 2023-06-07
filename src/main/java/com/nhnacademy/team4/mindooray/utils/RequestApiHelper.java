package com.nhnacademy.team4.mindooray.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Type;
import java.util.List;

/**
 * @author 김주호
 * @date 2023-06-06
 */
@Component
@RequiredArgsConstructor
public class RequestApiHelper {
    private final RestTemplate restTemplate;

    public HttpHeaders getDefaultHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));
        return httpHeaders;
    }

    public <T, R> R getResponse(String url, HttpMethod method, HttpHeaders headers, T requestBody, Class<R> clazz) {
        HttpEntity<T> requestEntity = new HttpEntity<>(requestBody, headers);
        return getBody(url, method, requestEntity, clazz);
    }

    public <R> R getResponse(String url, HttpMethod method, HttpHeaders headers, Class<R> clazz) {
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        return getBody(url, method, requestEntity, clazz);
    }

    public <T, R> R getResponse(String url, HttpMethod method, T requestBody, Class<R> clazz) {
        return getResponse(url, method, getDefaultHeaders(), requestBody, clazz);
    }

    public <R> R getResponse(String url, HttpMethod method, Class<R> clazz) {
        return getResponse(url, method, getDefaultHeaders(), clazz);
    }

    private <T, R> R getBody(String url, HttpMethod method, HttpEntity<T> requestEntity, Class<R> clazz) {
        ResponseEntity<R> exchange = restTemplate.exchange(
                url,
                method,
                requestEntity,
                new ParameterizedTypeReference<>() {
                    @Override
                    public Type getType() {
                        return clazz;
                    }
                });
        return exchange.getBody();
    }
}
