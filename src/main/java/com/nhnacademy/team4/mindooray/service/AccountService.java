package com.nhnacademy.team4.mindooray.service;

import com.nhnacademy.team4.mindooray.adapter.AccountAdapter;
import com.nhnacademy.team4.mindooray.controller.AccountDetails;
import com.nhnacademy.team4.mindooray.dto.request.CreateAccountRequest;
import com.nhnacademy.team4.mindooray.dto.response.account.AccountResponse;
import com.nhnacademy.team4.mindooray.dto.response.account.LoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class AccountService implements UserDetailsService {
    private final AccountAdapter accountAdapter;
    private final PasswordEncoder passwordEncoder;

    public AccountResponse register(CreateAccountRequest createAccountRequest) {
        createAccountRequest.setPassword(passwordEncoder.encode(createAccountRequest.getPassword()));
        return accountAdapter.register(createAccountRequest);
    }

    public long getAccountIdByLoginId(String loginId) {
        AccountResponse accountResponse = accountAdapter.getAccountByLoginId(loginId);
        return accountResponse.getId();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LoginResponse loginResponse = accountAdapter.getLoginInfoByLoginId(username);

        AccountDetails accountDetails = AccountDetails.create(loginResponse.getLoginId(), loginResponse.getPassword(),
                Collections.singleton(new SimpleGrantedAuthority(loginResponse.getRole())));
        if(!loginResponse.getStatus().equals("ACTIVE")) {
            accountDetails.setAccountNonExpire(false);
        }

        return accountDetails;
    }
}
