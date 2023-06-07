package com.nhnacademy.team4.mindooray.utils;

import lombok.Getter;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;

@Getter
public final class RestApiUrlBuilder<T> {
    private String url;
    private HttpEntity<T> entity;
    private HttpMethod method;

    private RestApiUrlBuilder() {}

    public static Builder builder() {
        return new Builder();
    }

    private RestApiUrlBuilder(Builder builder) {
        UriComponentsBuilder uri = UriComponentsBuilder.fromUriString(builder.url);
        uri = addParameters(uri, builder);
        UriComponents uriComponents = uri.buildAndExpand(builder.pathVariable);
        this.url = uriComponents.toUriString();
        this.method = builder.method;
        this.entity = new HttpEntity<>((T) builder.body, addHeaders(builder));
    }

    private UriComponentsBuilder addParameters(UriComponentsBuilder uri, Builder builder) {
        for(Map.Entry<String, Object> entry : builder.params.entrySet()) {
            String key = entry.getKey();
            String value = String.valueOf(entry.getValue());
            uri = uri.queryParam(key, value);
        }
        return uri;
    }

    private HttpHeaders addHeaders(Builder builder) {
        HttpHeaders headers = new HttpHeaders();
        for(Map.Entry<String, String> entry : builder.headers.entrySet()) {
            headers.add(entry.getKey(), entry.getValue());
        }
        return headers;
    }

    public static class Builder {
        private String url;
        private Map<String, Object> params;
        private Map<String, Object> pathVariable;
        private Map<String, String> headers;
        private HttpMethod method;
        private Object body;

        private Builder() {
            this.params = new HashMap<>();
            this.pathVariable = new HashMap<>();
            this.headers = new HashMap<>();
        }

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public Builder method(HttpMethod method) {
            this.method = method;
            return this;
        }

        public Builder param(String key, Object value) {
            this.params.put(key, value);
            return this;
        }

        public Builder pathVariable(String key, Object value) {
            this.pathVariable.put(key, value);
            return this;
        }

        public Builder header(String key, String value) {
            this.headers.put(key, value);
            return this;
        }

        public Builder body(Object body) {
            this.body = body;
            return this;
        }

        public <T> RestApiUrlBuilder<T> build() {
            return new RestApiUrlBuilder<>(this);
        }
    }
}
