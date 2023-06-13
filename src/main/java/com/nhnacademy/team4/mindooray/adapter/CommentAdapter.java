package com.nhnacademy.team4.mindooray.adapter;

import com.nhnacademy.team4.mindooray.dto.CommentCreateResponse;
import com.nhnacademy.team4.mindooray.dto.CreateCommentRequest;
import com.nhnacademy.team4.mindooray.dto.response.CommentResponse;
import com.nhnacademy.team4.mindooray.utils.RestApiUrlBuilder;
import com.nhnacademy.team4.mindooray.utils.RestApiUtils;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CommentAdapter {

    public List<CommentResponse> getTaskComments(Long taskId) {
        RestApiUrlBuilder<Void> builder = RestApiUrlBuilder.builder()
                .url("http://localhost:9091/tasks/{taskId}/comments")
                .method(HttpMethod.GET)
                .pathVariable("taskId", taskId)
                .build();

        return RestApiUtils.getExchangeList(builder, CommentResponse.class);
    }

    public CommentCreateResponse createComment(Long taskId, CreateCommentRequest createCommentRequest) {
        RestApiUrlBuilder<CreateCommentRequest> builder = RestApiUrlBuilder.builder()
                .url("http://localhost:9091/tasks/{taskId}/comments")
                .method(HttpMethod.POST)
                .header("Accept", "application/json")
                .pathVariable("taskId", taskId)
                .body(createCommentRequest)
                .build();

        return RestApiUtils.getExchange(builder, CommentCreateResponse.class);
    }
}
