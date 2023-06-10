package com.nhnacademy.team4.mindooray.adapter;

import com.nhnacademy.team4.mindooray.config.ApiProperties;
import com.nhnacademy.team4.mindooray.dto.request.CreateProjectRequest;
import com.nhnacademy.team4.mindooray.dto.response.project.ProjectMemberResponse;
import com.nhnacademy.team4.mindooray.dto.response.project.ProjectResponse;
import com.nhnacademy.team4.mindooray.utils.RestApiUrlBuilder;
import com.nhnacademy.team4.mindooray.utils.RestApiUtils;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProjectAdapter {
    private final String projectUrl;

    public ProjectAdapter(ApiProperties apiProperties) {
        this.projectUrl = apiProperties.getProject();
    }

    public ProjectMemberResponse checkProjectAccount(long accountId, long projectId) {
        RestApiUrlBuilder<Void> builder = RestApiUrlBuilder.builder()
                .url(projectUrl + "/{projectId}/role/{accountId}")
                .method(HttpMethod.GET)
                .header("Accept", "application/json")
                .pathVariable("projectId", projectId)
                .pathVariable("accountId", accountId)
                .build();

//        RestApiUtils.getExchange(builder, ProjectMemberResponse.class);

        ProjectMemberResponse response = new ProjectMemberResponse();
        response.setProjectRole("PROJECT_MEMBER");
        return response;
    }

    public ProjectResponse getAccountProject(long projectId) {
        RestApiUrlBuilder<Void> builder = RestApiUrlBuilder.builder()
                .url(projectUrl + "/{projectId}")
                .method(HttpMethod.GET)
                .header("Accept", "application/json")
                .pathVariable("projectId", projectId)
                .build();
//        RestApiUtils.getExchange(builder, ProjectResponse.class);

        ProjectResponse projectResponse = new ProjectResponse();
        projectResponse.setTitle("project title");
        return projectResponse;
    }

    public List<ProjectResponse> getAccountProjects(long accountId) {
        RestApiUrlBuilder<Void> builder = RestApiUrlBuilder.builder()
                .url(projectUrl)
                .method(HttpMethod.GET)
                .header("Accept", "application/json")
                .param("accountId", accountId)
                .build();

//        RestApiUtils.getExchangeList(builder, ProjectResponse.class);

        List<ProjectResponse> projectResponseList = new ArrayList<>();
        for(int i=0; i<10; i++) {
            ProjectResponse projectResponse = new ProjectResponse();
            projectResponse.setId(i + 1);
            projectResponse.setTitle("project title" + (i + 1));
            projectResponseList.add(projectResponse);
        }
        return projectResponseList;
    }

    public void createProject(long accountId, CreateProjectRequest createProjectRequest) {
        RestApiUrlBuilder<CreateProjectRequest> builder = RestApiUrlBuilder.builder()
                .url(projectUrl)
                .method(HttpMethod.POST)
                .header("Accept", "application/json")
                .param("accountId", accountId)
                .body(createProjectRequest)
                .build();

        RestApiUtils.getExchange(builder, String.class);
    }
}
