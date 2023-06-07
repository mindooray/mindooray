package com.nhnacademy.team4.mindooray.service;

import com.nhnacademy.team4.mindooray.adapter.GithubAdapter;
import com.nhnacademy.team4.mindooray.dto.response.account.GithubEmailResponse;
import com.nhnacademy.team4.mindooray.exception.AuthenticationNotFoundEmailException;
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
