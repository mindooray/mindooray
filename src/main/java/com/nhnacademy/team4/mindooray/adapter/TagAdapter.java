package com.nhnacademy.team4.mindooray.adapter;

import com.nhnacademy.team4.mindooray.dto.response.project.ProjectTagResponse;
import com.nhnacademy.team4.mindooray.utils.RestApiUrlBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TagAdapter {
    public List<ProjectTagResponse> getProjectTags(long projectId) {
        RestApiUrlBuilder<Void> builder = RestApiUrlBuilder.builder()
                .url("http://localhost:9091/projects/projects/{projectId}/tags")
                .method(HttpMethod.GET)
                .header("Accept", "application/json")
                .pathVariable("projectId", projectId)
                .build();

//        return RestApiUtils.getExchangeList(builder, ProjectTagResponse.class);

        List<ProjectTagResponse> tags = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ProjectTagResponse projectTagResponse = new ProjectTagResponse();
            projectTagResponse.setTagId((long) (i + 1));
            projectTagResponse.setTagName("tag" + (i + 1));

            tags.add(projectTagResponse);
        }
        return tags;
    }

}
