package com.nhnacademy.team4.mindooray.domain.comment.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CreateCommentRequest {
    private String content;
    private Long accountId;
}
