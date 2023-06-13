package com.nhnacademy.team4.mindooray.service;

import com.nhnacademy.team4.mindooray.adapter.AccountAdapter;
import com.nhnacademy.team4.mindooray.adapter.CommentAdapter;
import com.nhnacademy.team4.mindooray.dto.CommentCreateResponse;
import com.nhnacademy.team4.mindooray.dto.CommentDTO;
import com.nhnacademy.team4.mindooray.dto.CreateCommentRequest;
import com.nhnacademy.team4.mindooray.dto.response.CommentResponse;
import com.nhnacademy.team4.mindooray.dto.response.account.AccountResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentAdapter commentAdapter;
    private final AccountAdapter accountAdapter;

    public List<CommentDTO> getTaskComments(Long taskId) {
        List<CommentResponse> commentList = commentAdapter.getTaskComments(taskId);
        List<Long> accountIds = commentList.stream()
                .map(CommentResponse::getAccountId)
                .collect(Collectors.toList());
        if(accountIds.isEmpty()) {
            return new ArrayList<>();
        }

        List<AccountResponse> accountList = accountAdapter.getAccounts(accountIds);

        return commentList.stream()
                .map(c -> {
                    for(AccountResponse account : accountList) {
                        if(c.getAccountId().equals(account.getAccountId())) {
                            return CommentDTO.toDto(account, c);
                        }
                    }
                    throw new IllegalStateException("account id not ofound");
                })
                .collect(Collectors.toList());
    }

    public CommentCreateResponse createComment(Long accountId, Long taskId, CreateCommentRequest createCommentRequest) {
        createCommentRequest.setAccountId(accountId);
        return commentAdapter.createComment(taskId, createCommentRequest);
    }
}
