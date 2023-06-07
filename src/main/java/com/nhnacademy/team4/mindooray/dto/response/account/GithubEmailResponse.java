package com.nhnacademy.team4.mindooray.dto.response.account;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class GithubEmailResponse {
    String email;
    boolean verified;
    boolean primary;
    String visibility;
}
