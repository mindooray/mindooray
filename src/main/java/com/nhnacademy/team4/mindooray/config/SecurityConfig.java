package com.nhnacademy.team4.mindooray.config;

import com.nhnacademy.team4.mindooray.repository.RedisRepository;
import com.nhnacademy.team4.mindooray.handler.LoginSuccessHandler;
import com.nhnacademy.team4.mindooray.service.AccountOAuth2Service;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final RedisRepository redisRepository;
    private final AccountOAuth2Service accountOAuth2Service;
    private final OAuth2ClientProperties oAuth2ClientProperties;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                    .csrf()
                        .disable()
                    .authorizeHttpRequests()
                    .anyRequest().permitAll()
                .and()
                    .formLogin()
                    .loginPage("/login")
                    .loginProcessingUrl("/login")
                    .usernameParameter("loginId")
                    .passwordParameter("password")
                    .successHandler(new LoginSuccessHandler(redisRepository))
                .and()
                    .oauth2Login()
                    .loginPage("/login")
                    .successHandler(new LoginSuccessHandler(redisRepository))
                    .clientRegistrationRepository(clientRegistrationRepository())
                    .userInfoEndpoint()
                    .userService(accountOAuth2Service)
                    .and()
                .and()
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.NEVER)
                .and()
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        return new InMemoryClientRegistrationRepository(ClientRegistration.withClientRegistration(clientRegistration()).build());
    }

    public ClientRegistration clientRegistration() {
        return CommonOAuth2Provider.GITHUB.getBuilder("github")
                .clientId(oAuth2ClientProperties.getRegistration().get("github").getClientId())
                .clientSecret(oAuth2ClientProperties.getRegistration().get("github").getClientSecret())
                .build();
    }
}
