package com.nhnacademy.team4.mindooray.domain.comment.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CommentResponse {
    private Long commentId;
    private Long accountId;
    private String content;
    private LocalDateTime createTime;
}
