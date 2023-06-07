package com.nhnacademy.team4.mindooray.config;

import com.nhnacademy.team4.mindooray.handler.*;
import com.nhnacademy.team4.mindooray.manager.ProjectAuthorizationManager;
import com.nhnacademy.team4.mindooray.service.AccountOAuth2Service;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private static final String LOGIN_URI = "/login";

    private final AccountOAuth2Service accountOAuth2Service;
    private final LoginSuccessHandler loginSuccessHandler;
    private final AccountAuthenticationFailureHandler accountAuthenticationFailureHandler;
    private final AccountLogoutSuccessHandler accountLogoutSuccessHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf().disable()
                .authorizeHttpRequests(a -> a
                        .antMatchers("/", "/login", "/register").permitAll()

                        // 유저 관련 접근 제한
                        .antMatchers("/projects").hasAuthority("ROLE_USER")

                        // project 관련 접근 제한
                        .antMatchers(HttpMethod.PUT, "/projects/{projectId}/accounts")
                            .access(new ProjectAuthorizationManager("PROJECT_ADMIN"))

                        //task 관련 접근 제한
                        .antMatchers("/empty")
                            .access(new ProjectAuthorizationManager("PROJECT_MEMBER"))

                        // 댓글 관련 접근 제한
                        .antMatchers(HttpMethod.POST, "/empty")   // 생성
                            .access(new ProjectAuthorizationManager("PROJECT_MEMBER"))

                        .anyRequest().authenticated())
                .formLogin(h -> h
                        .loginPage(LOGIN_URI)
                        .loginProcessingUrl(LOGIN_URI)
                        .failureHandler(accountAuthenticationFailureHandler)
                        .usernameParameter("loginId")
                        .passwordParameter("password")
                        .successHandler(loginSuccessHandler)
                )
                .oauth2Login(h -> h
                        .loginPage(LOGIN_URI)
                        .successHandler(loginSuccessHandler)
                        .failureHandler(new OAuth2LoginFailure())
                        .userInfoEndpoint()
                        .userService(accountOAuth2Service))
                .sessionManagement(h -> h
                        .sessionFixation(SessionManagementConfigurer.SessionFixationConfigurer::none)
                )
                .logout(h -> h
                        .logoutUrl("/logout")
                        .logoutSuccessUrl(LOGIN_URI)
                        .logoutSuccessHandler(accountLogoutSuccessHandler)
                        .invalidateHttpSession(true)
                )
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
