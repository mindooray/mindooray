package com.nhnacademy.team4.mindooray.dto.response.account;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class AccountResponse {
    private long id;
    private String email;
    private String loginId;
    private String role;
}
