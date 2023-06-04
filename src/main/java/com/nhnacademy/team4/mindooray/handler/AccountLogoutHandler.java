package com.nhnacademy.team4.mindooray.handler;

import com.nhnacademy.team4.mindooray.repository.RedisRepository;
import com.nhnacademy.team4.mindooray.utils.CookieUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@RequiredArgsConstructor
public class AccountLogoutHandler implements LogoutHandler {
    private final RedisRepository redisRepository;
    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        Cookie cookie = CookieUtils.getCookie(request, "SESSION")
                .orElseThrow();
        redisRepository.remove(cookie.getValue());
        CookieUtils.remove(response, "SESSION");
    }
}
