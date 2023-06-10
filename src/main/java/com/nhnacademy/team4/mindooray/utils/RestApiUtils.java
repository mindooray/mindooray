package com.nhnacademy.team4.mindooray.utils;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public final class RestApiUtils {
    private RestApiUtils() {}

    public static <T> T getExchange(RestApiUrlBuilder<?> builder, Class<T> clz) {
        RestTemplate restTemplate = BeanUtils.getBean(RestTemplate.class);
        ResponseEntity<T> exchange;
        try {
            exchange = restTemplate.exchange(
                    builder.getUrl(),
                    builder.getMethod(),
                    builder.getEntity(),
                    new ParameterizedTypeReference<>() {
                        @Override
                        public Type getType() {
                            return clz;
                        }
                    });
        } catch (Exception e) {
            throw e;
        }

        return exchange.getBody();
    }

    public static <T> List<T> getExchangeList(RestApiUrlBuilder<?> builder, Class<T> clz) {
        RestTemplate restTemplate = getRestTemplateBean();
        ResponseEntity<List<T>> exchange = restTemplate.exchange(
                builder.getUrl(),
                builder.getMethod(),
                builder.getEntity(),
                new ParameterizedTypeReference<>() {
                    @Override
                    public Type getType() {
                        return new ParameterizedType() {
                            @Override
                            public Type[] getActualTypeArguments() {
                                return new Type[] {clz};
                            }

                            @Override
                            public Type getRawType() {
                                return List.class;
                            }

                            @Override
                            public Type getOwnerType() {
                                return null;
                            }
                        };
                    }
                });

        return exchange.getBody();
    }
    
    private static RestTemplate getRestTemplateBean() {
        return BeanUtils.getBean(RestTemplate.class);
    }
}
