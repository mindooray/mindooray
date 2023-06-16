package com.nhnacademy.team4.mindooray.domain.account.dto;

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
