package com.nhnacademy.team4.mindooray.domain.account.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginResponse {
    private String loginId;
    private String password;
    private String role;
    private String status;
}
