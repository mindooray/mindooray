package com.nhnacademy.team4.mindooray.domain.account.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpdateAccountRequest {
    private String loginId;
    private String email;
    private String password;
}
