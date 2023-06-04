package com.nhnacademy.team4.mindooray.service;

import com.nhnacademy.team4.mindooray.adapter.AccountAdapter;
import com.nhnacademy.team4.mindooray.domain.AccountDetails;
import com.nhnacademy.team4.mindooray.dto.request.CreateAccountRequest;
import com.nhnacademy.team4.mindooray.dto.response.AccountResponse;
import com.nhnacademy.team4.mindooray.dto.response.LoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LoginResponse loginResponse = accountAdapter.getAccountByLoginId(username);

        return AccountDetails.create(loginResponse.getLoginId(), loginResponse.getPassword(),
                Collections.singleton(new SimpleGrantedAuthority(loginResponse.getRole())));
    }

    public LoginResponse getAccountByEmail(String email) {
        return accountAdapter.getAccountByEmail(email);
    }
}
