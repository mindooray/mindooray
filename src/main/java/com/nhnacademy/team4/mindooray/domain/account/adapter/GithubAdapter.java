package com.nhnacademy.team4.mindooray.domain.account.adapter;

import com.nhnacademy.team4.mindooray.domain.account.dto.GithubEmailResponse;
import com.nhnacademy.team4.mindooray.global.utils.RestApiUrlBuilder;
import com.nhnacademy.team4.mindooray.global.utils.RestApiUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GithubAdapter {
    private static final String GITHUB_EMAIL_URL = "https://api.github.com/user/emails";

    /**
     * oAuth2 를 이용해 로그인한 계정의 깃허브 이메일 정보 요청
     * @param token
     * @return
     */
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
