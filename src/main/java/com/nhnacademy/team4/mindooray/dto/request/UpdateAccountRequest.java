package com.nhnacademy.team4.mindooray.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
public class UpdateAccountRequest {
    private String loginId;
    private String email;
    private String password;
}
