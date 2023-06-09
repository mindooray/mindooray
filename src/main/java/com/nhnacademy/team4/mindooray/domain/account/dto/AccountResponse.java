package com.nhnacademy.team4.mindooray.domain.account.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class AccountResponse {
    private Long accountId;
    private String email;
    private String loginId;
    private String role;
}
