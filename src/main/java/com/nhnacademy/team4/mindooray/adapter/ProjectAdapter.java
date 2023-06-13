package com.nhnacademy.team4.mindooray.adapter;

import com.nhnacademy.team4.mindooray.config.ApiProperties;
import com.nhnacademy.team4.mindooray.dto.request.CreateProjectRequest;
import com.nhnacademy.team4.mindooray.dto.response.project.ProjectAccountIdResponse;
import com.nhnacademy.team4.mindooray.dto.response.project.ProjectMemberResponse;
import com.nhnacademy.team4.mindooray.dto.response.project.ProjectResponse;
import com.nhnacademy.team4.mindooray.utils.RestApiUrlBuilder;
import com.nhnacademy.team4.mindooray.utils.RestApiUtils;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProjectAdapter {
    private final String projectUrl;
    private static final String PROJECT_URL_PREFIX = "/projects";

    public ProjectAdapter(ApiProperties apiProperties) {
        this.projectUrl = apiProperties.getProject() + PROJECT_URL_PREFIX;
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
        response.setProjectRole("PROJECT_ADMIN");
        return response;
    }

    public List<ProjectAccountIdResponse> getProjectAccountIds(long projectId) {
        RestApiUrlBuilder<Void> builder = RestApiUrlBuilder.builder()
                .url(projectUrl + "/{projectId}/accounts")
                .method(HttpMethod.GET)
                .header("Accept", "application/json")
                .pathVariable("projectId", projectId)
                .build();
        return RestApiUtils.getExchangeList(builder, ProjectAccountIdResponse.class);
    }

    public List<ProjectResponse> getAccountProjects(long accountId) {
        RestApiUrlBuilder<Void> builder = RestApiUrlBuilder.builder()
                .url(projectUrl)
                .method(HttpMethod.GET)
                .header("Accept", "application/json")
                .param("accountId", accountId)
                .build();

        return RestApiUtils.getExchangeList(builder, ProjectResponse.class);
    }

    public void createProject(CreateProjectRequest createProjectRequest) {
        RestApiUrlBuilder<CreateProjectRequest> builder = RestApiUrlBuilder.builder()
                .url(projectUrl)
                .method(HttpMethod.POST)
                .header("Accept", "application/json")
                .body(createProjectRequest)
                .build();

        RestApiUtils.getExchange(builder, CreateProjectRequest.class);
    }

    public void addProjectAccount(long accountId, long projectId) {
        RestApiUrlBuilder<Void> builder = RestApiUrlBuilder.builder()
                .url(projectUrl + "/projects/{projectId}/account/{accountId}")
                .method(HttpMethod.POST)
                .pathVariable("projectId", projectId)
                .pathVariable("accountId", accountId)
                .build();

        RestApiUtils.getExchange(builder, null);
    }
}
