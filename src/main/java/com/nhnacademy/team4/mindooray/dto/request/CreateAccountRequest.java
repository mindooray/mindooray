package com.nhnacademy.team4.mindooray.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CreateAccountRequest {
    private String loginId;
    private String email;
    private String password;
}
