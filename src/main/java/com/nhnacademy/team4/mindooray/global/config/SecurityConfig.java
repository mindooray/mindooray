package com.nhnacademy.team4.mindooray.global.config;

import com.nhnacademy.team4.mindooray.global.handler.AccountAuthenticationFailureHandler;
import com.nhnacademy.team4.mindooray.global.handler.AccountLogoutSuccessHandler;
import com.nhnacademy.team4.mindooray.global.handler.LoginSuccessHandler;
import com.nhnacademy.team4.mindooray.global.handler.OAuth2LoginFailure;
import com.nhnacademy.team4.mindooray.global.manager.ExceptionFilter;
import com.nhnacademy.team4.mindooray.global.manager.ProjectAuthorizationManager;
import com.nhnacademy.team4.mindooray.domain.account.service.AccountOAuth2Service;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {
    private static final String LOGIN_URI = "/login";

    private final AccountOAuth2Service accountOAuth2Service;
    private final LoginSuccessHandler loginSuccessHandler;
    private final AccountAuthenticationFailureHandler accountAuthenticationFailureHandler;
    private final AccountLogoutSuccessHandler accountLogoutSuccessHandler;
    private final PermissionEvaluator permissionEvaluator;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf().disable()
                .addFilterBefore(new ExceptionFilter(), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(h -> h
                        .accessDeniedPage("/403"))
                .authorizeHttpRequests(a -> a
                        .antMatchers("/css/**", "/img/**").permitAll()
                        .antMatchers("/login", "/register").permitAll()
                        // 유저 관련 접근 제한
                        .antMatchers("/my-page").hasAuthority("ROLE_USER")

                        // project 관련 접근 제한
                        .antMatchers("/projects/create").hasAuthority("ROLE_USER")
                        .antMatchers("/projects").hasAuthority("ROLE_USER")
                        .antMatchers("/projects/{projectId}")
                            .access(new ProjectAuthorizationManager("projectId", "PROJECT_ADMIN"))
                        .antMatchers("/projects/{projectId}/accounts")
                            .access(new ProjectAuthorizationManager("projectId","PROJECT_MEMBER"))
                        .antMatchers(HttpMethod.POST, "/projects/{projectId}/accounts/{accountId}")
                            .access(new ProjectAuthorizationManager("projectId", "PROJECT_ADMIN"))

                        //task 관련 접근 제한
                        .antMatchers("/projects/{projectId}/tasks")
                            .access(new ProjectAuthorizationManager("projectId", "PROJECT_MEMBER"))
                        .antMatchers("/projects/{projectId}/create-task")
                            .access(new ProjectAuthorizationManager("projectId", "PROJECT_MEMBER"))

                        // 댓글 관련 접근 제한
                        .antMatchers(HttpMethod.POST, "/empty")   // 생성
                            .access(new ProjectAuthorizationManager("projectId", "PROJECT_MEMBER"))

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
                        .logoutSuccessHandler(accountLogoutSuccessHandler)
                        .invalidateHttpSession(true)
                )
                .build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        DefaultWebSecurityExpressionHandler expressionHandler = new DefaultWebSecurityExpressionHandler();
        expressionHandler.setPermissionEvaluator(permissionEvaluator);

        return (web) -> web.expressionHandler(expressionHandler);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
