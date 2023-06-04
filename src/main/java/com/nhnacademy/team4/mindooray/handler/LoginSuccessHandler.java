package com.nhnacademy.team4.mindooray.handler;

import com.nhnacademy.team4.mindooray.RedisRepository;
import com.nhnacademy.team4.mindooray.utils.CookieUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    private static final int EXPIRE_TIME_SECOND = 60;
    private static final String SESSION_NAME = "SESSION";
    private static final String ACCOUNT_ID = "accountId";
    private final RedisRepository redisRepository;

    public LoginSuccessHandler(RedisRepository redisRepository) {
        this.redisRepository = redisRepository;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String sessionId = UUID.randomUUID().toString();
        CookieUtils.save(response, SESSION_NAME, sessionId, EXPIRE_TIME_SECOND);

        redisRepository.save(sessionId, ACCOUNT_ID, authentication.getName(), EXPIRE_TIME_SECOND);

        super.onAuthenticationSuccess(request, response, authentication);
    }
}
