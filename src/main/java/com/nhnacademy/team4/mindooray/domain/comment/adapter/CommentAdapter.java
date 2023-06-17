package com.nhnacademy.team4.mindooray.domain.comment.adapter;

import com.nhnacademy.team4.mindooray.domain.comment.dto.CommentCreateResponse;
import com.nhnacademy.team4.mindooray.domain.comment.dto.CreateCommentRequest;
import com.nhnacademy.team4.mindooray.domain.comment.dto.CommentResponse;
import com.nhnacademy.team4.mindooray.global.config.ApiProperties;
import com.nhnacademy.team4.mindooray.global.utils.RestApiUrlBuilder;
import com.nhnacademy.team4.mindooray.global.utils.RestApiUtils;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CommentAdapter {

    private final String projectUrl;

    public CommentAdapter(ApiProperties apiProperties) {
        projectUrl = apiProperties.getProject();
    }

    public List<CommentResponse> getTaskComments(Long taskId) {
        RestApiUrlBuilder<Void> builder = RestApiUrlBuilder.builder()
                .url(projectUrl + "/tasks/{taskId}/comments")
                .method(HttpMethod.GET)
                .pathVariable("taskId", taskId)
                .build();

        return RestApiUtils.getExchangeList(builder, CommentResponse.class);
    }

    public CommentCreateResponse createComment(Long taskId, CreateCommentRequest createCommentRequest) {
        RestApiUrlBuilder<CreateCommentRequest> builder = RestApiUrlBuilder.builder()
                .url(projectUrl + "/tasks/{taskId}/comments")
                .method(HttpMethod.POST)
                .header("Accept", "application/json")
                .pathVariable("taskId", taskId)
                .body(createCommentRequest)
                .build();

        return RestApiUtils.getExchange(builder, CommentCreateResponse.class);
    }
}
