package com.nhnacademy.team4.mindooray.config;

import com.nhnacademy.team4.mindooray.handler.AccountLogoutHandler;
import com.nhnacademy.team4.mindooray.repository.RedisRepository;
import com.nhnacademy.team4.mindooray.handler.LoginSuccessHandler;
import com.nhnacademy.team4.mindooray.service.AccountOAuth2Service;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
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
    private static final String GITHUB = "github";
    private static final String LOGIN_URI = "/login";
    private final RedisRepository redisRepository;
    private final AccountOAuth2Service accountOAuth2Service;
    private final OAuth2ClientProperties oAuth2ClientProperties;
    private final LoginSuccessHandler loginSuccessHandler;
    private final AccountLogoutHandler accountLogoutHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                    .csrf()
                        .disable()
                    .authorizeHttpRequests()
                    .anyRequest().permitAll()
                .and()
                    .formLogin()
                    .loginPage(LOGIN_URI)
                    .loginProcessingUrl(LOGIN_URI)
                    .usernameParameter("loginId")
                    .passwordParameter("password")
                    .successHandler(loginSuccessHandler)
                .and()
                    .oauth2Login()
                    .loginPage(LOGIN_URI)
                    .successHandler(loginSuccessHandler)
                    .clientRegistrationRepository(clientRegistrationRepository())
                    .userInfoEndpoint()
                    .userService(accountOAuth2Service)
                    .and()
                .and()
                    .sessionManagement()
                    .sessionFixation()
                    .none()
                .and()
                    .logout()
                    .logoutUrl("/logout")
                    .addLogoutHandler(accountLogoutHandler)
                    .logoutSuccessUrl("/login")
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
        return CommonOAuth2Provider.GITHUB.getBuilder(GITHUB)
                .clientId(oAuth2ClientProperties.getRegistration().get(GITHUB).getClientId())
                .clientSecret(oAuth2ClientProperties.getRegistration().get(GITHUB).getClientSecret())
                .build();
    }
}
