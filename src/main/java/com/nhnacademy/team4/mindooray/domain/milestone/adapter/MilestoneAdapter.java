package com.nhnacademy.team4.mindooray.domain.milestone.adapter;

import com.nhnacademy.team4.mindooray.domain.milestone.dto.MilestoneResponse;
import com.nhnacademy.team4.mindooray.global.config.ApiProperties;
import com.nhnacademy.team4.mindooray.global.utils.RestApiUrlBuilder;
import com.nhnacademy.team4.mindooray.global.utils.RestApiUtils;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

@Component
public class MilestoneAdapter {
    private final String projectUrl;

    public MilestoneAdapter(ApiProperties apiProperties) {
        this.projectUrl = apiProperties.getProject();
    }

    public MilestoneResponse getTaskMilestone(Long taskId) {
        RestApiUrlBuilder<Void> builder = RestApiUrlBuilder.builder()
                .url(projectUrl + "/tasks/{taskId}/milestones")
                .method(HttpMethod.GET)
                .pathVariable("taskId", taskId)
                .build();

        return RestApiUtils.getExchange(builder, MilestoneResponse.class);
    }
}
