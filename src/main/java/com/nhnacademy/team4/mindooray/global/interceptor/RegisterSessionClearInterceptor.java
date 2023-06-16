package com.nhnacademy.team4.mindooray.global.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Objects;

public class RegisterSessionClearInterceptor implements HandlerInterceptor {
    /**
     * 깃 이메일이 없어 리다이렉트 되어 세션에 깃 이메일이 남아있는 경우 삭제 해 주는 인터셉터
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HttpSession session = request.getSession(false);
        if(Objects.nonNull(session) && Objects.nonNull(session.getAttribute("github"))) {
            session.removeAttribute("github");
        }
    }
}
