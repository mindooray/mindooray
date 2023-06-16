package com.nhnacademy.team4.mindooray.global.handler;

import com.nhnacademy.team4.mindooray.global.repository.RedisRepository;
import com.nhnacademy.team4.mindooray.global.utils.CookieUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class AccountLogoutSuccessHandler implements LogoutSuccessHandler {
    private final RedisRepository redisRepository;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Cookie cookie = CookieUtils.getCookie(request, "SESSION")
                .orElseThrow();
        redisRepository.remove(cookie.getValue());
        CookieUtils.remove(response, "SESSION");

        response.sendRedirect("/");
    }
}
