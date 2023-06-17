package com.nhnacademy.team4.mindooray.domain.account.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

public class AccountDetails implements UserDetails, OAuth2User {
    private String username;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;
    private Map<String, Object> attributes;
    private boolean accountNonExpire;

    protected AccountDetails(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super();
        this.username = username;
        this.password = password;
        this.authorities = authorities;
        this.accountNonExpire = true;
    }

    public static AccountDetails create(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        return new AccountDetails(username, password, authorities);
    }

    public static AccountDetails create(String username, String password, Collection<? extends GrantedAuthority> authorities, Map<String, Object> attributes) {
        AccountDetails accountDetails = new AccountDetails(username, password, authorities);
        accountDetails.attributes = attributes;
        return accountDetails;
    }

    public void setAccountNonExpire(boolean accountNonExpire) {
        this.accountNonExpire = accountNonExpire;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return this.attributes;
    }

    @Override
    public String getName() {
        return this.username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpire;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
