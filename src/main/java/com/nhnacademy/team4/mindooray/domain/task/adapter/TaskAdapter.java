package com.nhnacademy.team4.mindooray.domain.task.adapter;

import com.nhnacademy.team4.mindooray.global.config.ApiProperties;
import com.nhnacademy.team4.mindooray.domain.task.dto.CreateTaskRequest;
import com.nhnacademy.team4.mindooray.domain.task.dto.TaskResponse;
import com.nhnacademy.team4.mindooray.global.utils.RestApiUrlBuilder;
import com.nhnacademy.team4.mindooray.global.utils.RestApiUtils;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TaskAdapter {
    private final String taskUrl;
    private static final String TASK_URL_PREFIX = "/projects/{projectId}/tasks";

    public TaskAdapter(ApiProperties apiProperties) {
        this.taskUrl = apiProperties.getProject() + TASK_URL_PREFIX;
    }

    public List<TaskResponse> getTasks(long projectId) {
        RestApiUrlBuilder<Void> builder = RestApiUrlBuilder.builder()
                .url(taskUrl)
                .method(HttpMethod.GET)
                .header("Accept", "application/json")
                .pathVariable("projectId", projectId)
                .build();

        return RestApiUtils.getExchangeList(builder, TaskResponse.class);
    }

    public TaskResponse getTask(long projectId, long taskId) {
        RestApiUrlBuilder<Void> builder = RestApiUrlBuilder.builder()
                .url(taskUrl + "/{taskId}")
                .method(HttpMethod.GET)
                .header("Accept", "application/json")
                .pathVariable("projectId", projectId)
                .pathVariable("taskId", taskId)
                .build();

        return RestApiUtils.getExchange(builder, TaskResponse.class);
    }

    public void createTask(Long projectId, CreateTaskRequest createTaskRequest) {
        RestApiUrlBuilder<CreateTaskRequest> builder = RestApiUrlBuilder.builder()
                .url(taskUrl)
                .method(HttpMethod.POST)
                .pathVariable("projectId", projectId)
                .body(createTaskRequest)
                .build();

        RestApiUtils.getExchange(builder, null);
    }
}
