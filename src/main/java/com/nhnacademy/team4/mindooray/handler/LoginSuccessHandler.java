package com.nhnacademy.team4.mindooray.handler;

import com.nhnacademy.team4.mindooray.adapter.AccountAdapter;
import com.nhnacademy.team4.mindooray.dto.response.account.AccountResponse;
import com.nhnacademy.team4.mindooray.repository.RedisRepository;
import com.nhnacademy.team4.mindooray.utils.CookieUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    private static final int EXPIRE_TIME_SECOND = 60;
    private static final String SESSION_NAME = "SESSION";
    private static final String ACCOUNT_ID = "accountId";
    private final RedisRepository redisRepository;
    private final AccountAdapter accountAdapter;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String sessionId = UUID.randomUUID().toString();

        CookieUtils.save(response, SESSION_NAME, sessionId, EXPIRE_TIME_SECOND);
        AccountResponse accountResponse = accountAdapter.getAccountByLoginId(authentication.getName());
        long accountId = accountResponse.getId();
        redisRepository.save(sessionId, ACCOUNT_ID, accountId, EXPIRE_TIME_SECOND);

        super.onAuthenticationSuccess(request, response, authentication);
    }
}
