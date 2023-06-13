package com.nhnacademy.team4.mindooray.adapter;

import com.nhnacademy.team4.mindooray.dto.request.CreateTagRequest;
import com.nhnacademy.team4.mindooray.dto.response.project.ProjectTagResponse;
import com.nhnacademy.team4.mindooray.utils.RestApiUrlBuilder;
import com.nhnacademy.team4.mindooray.utils.RestApiUtils;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TagAdapter {
    public List<ProjectTagResponse> getProjectTags(long projectId) {
        RestApiUrlBuilder<Void> builder = RestApiUrlBuilder.builder()
                .url("http://localhost:9091/projects/{projectId}/tags")
                .method(HttpMethod.GET)
                .header("Accept", "application/json")
                .pathVariable("projectId", projectId)
                .build();

        return RestApiUtils.getExchangeList(builder, ProjectTagResponse.class);
    }

    public void createTag(CreateTagRequest createTagRequest) {
        RestApiUrlBuilder<CreateTagRequest> builder = RestApiUrlBuilder.builder()
                .url("http://localhost:9091/tags")
                .method(HttpMethod.POST)
                .body(createTagRequest)
                .build();

        RestApiUtils.getExchange(builder, null);
    }

    public void deleteTag(Long tagId) {
        RestApiUrlBuilder<Void> builder = RestApiUrlBuilder.builder()
                .url("http://localhost:9091/tags/{tagId}")
                .method(HttpMethod.DELETE)
                .pathVariable("tagId", tagId)
                .build();

        RestApiUtils.getExchange(builder, null);
    }
}
