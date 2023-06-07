package com.nhnacademy.team4.mindooray.config;

import com.nhnacademy.team4.mindooray.interceptor.RegisterSessionClearInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        WebMvcConfigurer.super.addInterceptors(registry);

        registry.addInterceptor(new RegisterSessionClearInterceptor())
                .addPathPatterns("/register/**");
    }
}
