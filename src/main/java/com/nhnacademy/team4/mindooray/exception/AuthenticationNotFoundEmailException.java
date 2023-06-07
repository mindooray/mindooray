package com.nhnacademy.team4.mindooray.exception;

import org.springframework.security.core.AuthenticationException;

public class AuthenticationNotFoundEmailException extends AuthenticationException {
    private String githubEmail;

    public AuthenticationNotFoundEmailException(String msg, String githubEmail) {
        super(msg);
        this.githubEmail = githubEmail;
    }

    public String getGithubEmail() {
        return githubEmail;
    }
}
