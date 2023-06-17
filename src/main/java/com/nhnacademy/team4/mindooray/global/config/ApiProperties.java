package com.nhnacademy.team4.mindooray.global.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "nhn.api.domain")
public class ApiProperties {
    private String account;
    private String project;
}
