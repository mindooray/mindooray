package com.nhnacademy.team4.mindooray.domain.account.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class UserInfoResponse {
    String login;
    long id;
    String email;
}
