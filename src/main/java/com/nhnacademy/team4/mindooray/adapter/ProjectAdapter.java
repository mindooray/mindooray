package com.nhnacademy.team4.mindooray.adapter;

import com.nhnacademy.team4.mindooray.config.ApiProperties;
import com.nhnacademy.team4.mindooray.dto.response.project.ProjectMemberResponse;
import com.nhnacademy.team4.mindooray.dto.response.project.ProjectResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ProjectAdapter {
    private final RestTemplate restTemplate;
    private final String projectUrl;

    public ProjectAdapter(RestTemplate restTemplate, ApiProperties apiProperties) {
        this.restTemplate = restTemplate;
        this.projectUrl = apiProperties.getProject();
    }

    public ProjectMemberResponse checkProjectAccount(long accountId, long projectId) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", "application/json");
        HttpEntity<Void> entity = new HttpEntity<>(headers);

//        ResponseEntity<ProjectMemberResponse> exchange = restTemplate.exchange(
//                projectUrl + "/{projectId}/role/{accountId}",
//                HttpMethod.GET,
//                entity,
//                new ParameterizedTypeReference<>() {
//                },
//                projectId, accountId);
//
//        return exchange.getBody();
        ProjectMemberResponse response = new ProjectMemberResponse();
        response.setProjectRole("PROJECT_ADMIN");
        return response;
    }

    public ProjectResponse getAccountProjects(long accountId) {
        return null;
    }

    public ProjectResponse getAccountProject(long accountId, long projectId) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", "application/json");
        HttpEntity<Void> entity = new HttpEntity<>(headers);

//        ResponseEntity<ProjectMemberResponse> exchange = restTemplate.exchange(
//                projectUrl + "/{projectId}/role/{accountId}",
//                HttpMethod.GET,
//                entity,
//                new ParameterizedTypeReference<>() {
//                },
//                projectId, accountId);
//
//        return exchange.getBody();
        ProjectResponse projectResponse = new ProjectResponse();
        projectResponse.setTitle("project title");
        return projectResponse;
    }
}
