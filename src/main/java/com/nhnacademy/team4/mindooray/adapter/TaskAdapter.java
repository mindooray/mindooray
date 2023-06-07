package com.nhnacademy.team4.mindooray.adapter;

import com.nhnacademy.team4.mindooray.config.ApiProperties;
import com.nhnacademy.team4.mindooray.dto.task.TaskResponse;
import com.nhnacademy.team4.mindooray.utils.RestApiUrlBuilder;
import com.nhnacademy.team4.mindooray.utils.RestApiUtils;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TaskAdapter {
    private final String projectUrl;

    public TaskAdapter(ApiProperties apiProperties) {
        this.projectUrl = apiProperties.getProject();
    }

    public List<TaskResponse> getTasks(long projectId) {
        RestApiUrlBuilder<Void> builder = RestApiUrlBuilder.builder()
                .url(projectUrl + "/{projectId}/tasks")
                .method(HttpMethod.GET)
                .header("Accept", "application/json")
                .pathVariable("projectId", projectId)
                .build();

//        RestApiUtils.getExchangeList(builder, TaskResponse.class);
        List<TaskResponse> taskResponseList = new ArrayList<>();
        for(int i=0; i<10; i++) {
            TaskResponse taskResponse = new TaskResponse();
            taskResponse.setId(i + 1);
            taskResponse.setTitle("task title" + (i + 1));
            taskResponse.setProjectTitle("project title" + (i + 1));
            taskResponse.setNumber(i + 1);
            taskResponse.setRepresentative("김강민");
            taskResponse.setStatus("status");
            taskResponseList.add(taskResponse);
        }

        return taskResponseList;
    }
}
