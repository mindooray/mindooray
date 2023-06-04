package com.nhnacademy.team4.mindooray.service;

import com.nhnacademy.team4.mindooray.adapter.AccountAdapter;
import com.nhnacademy.team4.mindooray.domain.AccountDetails;
import com.nhnacademy.team4.mindooray.dto.response.LoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class AccountOAuth2Service extends DefaultOAuth2UserService {
    private final AccountAdapter accountAdapter;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        String email = oAuth2User.getAttribute("email");
        LoginResponse loginResponse = accountAdapter.getAccountByEmail(email);

        return AccountDetails.create(loginResponse.getLoginId(), loginResponse.getPassword(),
                Collections.singleton(new SimpleGrantedAuthority(loginResponse.getRole())),
                oAuth2User.getAttributes());
    }
}
