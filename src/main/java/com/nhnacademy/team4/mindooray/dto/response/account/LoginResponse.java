package com.nhnacademy.team4.mindooray.dto.response.account;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginResponse {
    private String loginId;
    private String password;
    private String role;
    private String status = "ACTIVE";

    // /auth/login/{loginId} -> 로그인하는 아이디, 암호화된 password, role
    // /accounts/login/{loginId} -> pk 아이디
}
