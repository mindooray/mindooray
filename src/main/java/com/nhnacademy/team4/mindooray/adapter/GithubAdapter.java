package com.nhnacademy.team4.mindooray.adapter;

import com.nhnacademy.team4.mindooray.dto.response.account.GithubEmailResponse;
import com.nhnacademy.team4.mindooray.utils.RestApiUrlBuilder;
import com.nhnacademy.team4.mindooray.utils.RestApiUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GithubAdapter {
    private static final String GITHUB_EMAIL_URL = "https://api.github.com/user/emails";
    public List<GithubEmailResponse> getGithubEmails(String token) {
        RestApiUrlBuilder<GithubEmailResponse> build = RestApiUrlBuilder.builder()
                .url(GITHUB_EMAIL_URL)
                .method(HttpMethod.GET)
                .header("Accept", "application/vnd.github+json")
                .header("Authorization", "Bearer " + token)
                .header("X-GitHub-Api-Version", "2022-11-28")
                .build();

        return RestApiUtils.getExchangeList(
                build,
                GithubEmailResponse.class
                );
    }
}
