package com.nhnacademy.team4.mindooray.domain.account.service;

import com.nhnacademy.team4.mindooray.domain.account.adapter.GithubAdapter;
import com.nhnacademy.team4.mindooray.domain.account.dto.GithubEmailResponse;
import com.nhnacademy.team4.mindooray.global.exception.AuthenticationNotFoundEmailException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GithubService {
    private final GithubAdapter githubAdapter;

    public String getPrimaryEmail(String token) {
        List<GithubEmailResponse> emailResponseList = githubAdapter.getGithubEmails(token);

        for (GithubEmailResponse emailResponse : emailResponseList) {
            if(emailResponse.isPrimary()) {
                return emailResponse.getEmail();
            }
        }

        throw new AuthenticationNotFoundEmailException("not found email", null);
    }
}
