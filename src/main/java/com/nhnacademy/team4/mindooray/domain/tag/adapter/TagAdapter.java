package com.nhnacademy.team4.mindooray.domain.tag.adapter;

import com.nhnacademy.team4.mindooray.domain.task.dto.TaskTagRequest;
import com.nhnacademy.team4.mindooray.domain.tag.dto.TagRequest;
import com.nhnacademy.team4.mindooray.domain.tag.dto.TagResponse;
import com.nhnacademy.team4.mindooray.global.config.ApiProperties;
import com.nhnacademy.team4.mindooray.global.utils.RestApiUrlBuilder;
import com.nhnacademy.team4.mindooray.global.utils.RestApiUtils;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TagAdapter {
    private final String projectUrl;

    public TagAdapter(ApiProperties apiProperties) {
        projectUrl = apiProperties.getProject();
    }

    public List<TagResponse> getProjectTags(long projectId) {
        RestApiUrlBuilder<Void> builder = RestApiUrlBuilder.builder()
                .url(projectUrl + "/projects/{projectId}/tags")
                .method(HttpMethod.GET)
                .header("Accept", "application/json")
                .pathVariable("projectId", projectId)
                .build();

        return RestApiUtils.getExchangeList(builder, TagResponse.class);
    }

    public void createTag(Long projectId, TagRequest tagRequest) {
        RestApiUrlBuilder<TagRequest> builder = RestApiUrlBuilder.builder()
                .url(projectUrl + "/projects/{projectId}/tags")
                .method(HttpMethod.POST)
                .pathVariable("projectId", projectId)
                .body(tagRequest)
                .build();

        RestApiUtils.getExchange(builder, null);
    }

    public void deleteTag(Long tagId) {
        RestApiUrlBuilder<Void> builder = RestApiUrlBuilder.builder()
                .url(projectUrl + "/tags/{tagId}")
                .method(HttpMethod.DELETE)
                .pathVariable("tagId", tagId)
                .build();

        RestApiUtils.getExchange(builder, null);
    }

    public List<TagResponse> getTaskTags(Long taskId) {
        RestApiUrlBuilder<Void> builder = RestApiUrlBuilder.builder()
                .url(projectUrl + "/tasks/{taskId}/tags")
                .method(HttpMethod.GET)
                .pathVariable("taskId", taskId)
                .build();

        return RestApiUtils.getExchangeList(builder, TagResponse.class);
    }

    public void updateTag(Long tagId, TagRequest tagRequest) {
        RestApiUrlBuilder<Void> builder = RestApiUrlBuilder.builder()
                .url(projectUrl + "/tags/{tagId}")
                .method(HttpMethod.PUT)
                .pathVariable("tagId", tagId)
                .body(tagRequest)
                .build();

        RestApiUtils.getExchange(builder, null);
    }

    public void addTaskTags(Long taskId, TaskTagRequest taskTagRequest) {
        RestApiUrlBuilder<TaskTagRequest> builder = RestApiUrlBuilder.builder()
                .url(projectUrl + "/tasks/{taskId}/tags")
                .method(HttpMethod.POST)
                .pathVariable("taskId", taskId)
                .body(taskTagRequest)
                .build();

        RestApiUtils.getExchange(builder, null);
    }
}
