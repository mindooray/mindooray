package com.nhnacademy.team4.mindooray.domain.comment.controller;

import com.nhnacademy.team4.mindooray.domain.comment.dto.CommentCreateResponse;
import com.nhnacademy.team4.mindooray.domain.comment.dto.CreateCommentRequest;
import com.nhnacademy.team4.mindooray.global.repository.RedisRepository;
import com.nhnacademy.team4.mindooray.domain.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final RedisRepository redisRepository;

    @PostMapping("/tasks/{taskId}/comments")
    public String createComment(
            @CookieValue("SESSION") String key,
            @PathVariable("taskId") Long taskId,
            @ModelAttribute CreateCommentRequest createCommentRequest
    ) {
        Long accountId = redisRepository.getSessionAccountId(key);
        CommentCreateResponse commentCreateResponse = commentService.createComment(accountId, taskId, createCommentRequest);

        return "redirect:/projects/" + commentCreateResponse.getProjectId() + "/tasks/" + taskId;
    }

}
