package com.nhnacademy.team4.mindooray.dto;

import com.nhnacademy.team4.mindooray.dto.response.CommentResponse;
import com.nhnacademy.team4.mindooray.dto.response.account.AccountResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class CommentDTO {
    private Long id;
    private String accountName;
    private String content;
    private LocalDateTime createTime;

    public static CommentDTO toDto(AccountResponse accountResponse, CommentResponse commentResponse) {
        CommentDTO dto = new CommentDTO();
        dto.id = commentResponse.getCommentId();
        dto.accountName = accountResponse.getLoginId();
        dto.content = commentResponse.getContent();
        dto.createTime = commentResponse.getCreateTime();

        return dto;
    }
}
