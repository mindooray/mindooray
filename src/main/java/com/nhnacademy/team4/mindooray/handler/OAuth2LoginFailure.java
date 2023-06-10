package com.nhnacademy.team4.mindooray.handler;

import com.nhnacademy.team4.mindooray.exception.AuthenticationNotFoundEmailException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class OAuth2LoginFailure implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        if (exception instanceof AuthenticationNotFoundEmailException) {
            String githubEmail = ((AuthenticationNotFoundEmailException) exception).getGithubEmail();
            notFoundEmailExceptionRedirectWithGithubEmail(request, response, githubEmail);
        }
    }

    private void notFoundEmailExceptionRedirectWithGithubEmail(HttpServletRequest request, HttpServletResponse response, String email) throws IOException {
        HttpSession session = request.getSession(true);
        session.setAttribute("github", email);

        response.sendRedirect("/register");
    }
}
