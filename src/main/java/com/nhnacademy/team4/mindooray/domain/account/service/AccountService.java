package com.nhnacademy.team4.mindooray.domain.account.service;

import com.nhnacademy.team4.mindooray.domain.account.adapter.AccountAdapter;
import com.nhnacademy.team4.mindooray.domain.account.domain.AccountDetails;
import com.nhnacademy.team4.mindooray.domain.account.dto.CreateAccountRequest;
import com.nhnacademy.team4.mindooray.domain.account.dto.UpdateAccountRequest;
import com.nhnacademy.team4.mindooray.domain.account.dto.AccountResponse;
import com.nhnacademy.team4.mindooray.domain.account.dto.LoginResponse;
import com.nhnacademy.team4.mindooray.global.exception.DuplicateAccountException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountService implements UserDetailsService {
    private final AccountAdapter accountAdapter;
    private final PasswordEncoder passwordEncoder;

    public AccountResponse register(CreateAccountRequest createAccountRequest) {
        createAccountRequest.setPassword(passwordEncoder.encode(createAccountRequest.getPassword()));
//        try {
            return accountAdapter.register(createAccountRequest);
//        } catch (Exception e) {
//            throw new DuplicateAccountException("duplicate account exception");
//        }
    }

    public AccountResponse getAccount(long accountId) {
        return accountAdapter.getAccountById(accountId);
    }

    public List<AccountResponse> getAccounts(List<Long> accountIds) {
        return accountAdapter.getAccounts(accountIds);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LoginResponse loginResponse = null;
        try {
            loginResponse = accountAdapter.getLoginInfoByLoginId(username);
        } catch (Exception e) {
            throw new UsernameNotFoundException("not found account id");
        }

        AccountDetails accountDetails = AccountDetails.create(loginResponse.getLoginId(), loginResponse.getPassword(),
                Collections.singleton(new SimpleGrantedAuthority(loginResponse.getRole())));

        if (!loginResponse.getStatus().equals("ACTIVE")) {
            accountDetails.setAccountNonExpire(false);
        }

        return accountDetails;
    }

    public void updateAccount(long accountId, UpdateAccountRequest updateAccountRequest) {
        updateAccountRequest.setPassword(passwordEncoder.encode(updateAccountRequest.getPassword()));
        accountAdapter.updateAccount(accountId, updateAccountRequest);
    }

    public List<AccountResponse> getNoneProjectAccounts(List<Long> projectAccountIdList) {
        List<AccountResponse> accounts = accountAdapter.getAccounts();

        return accounts.stream()
                .filter(a -> !projectAccountIdList.contains(a.getAccountId()))
                .collect(Collectors.toList());
    }
}
