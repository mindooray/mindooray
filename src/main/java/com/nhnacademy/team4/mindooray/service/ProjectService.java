package com.nhnacademy.team4.mindooray.service;

import com.nhnacademy.team4.mindooray.adapter.ProjectAdapter;
import com.nhnacademy.team4.mindooray.dto.request.CreateProjectRequest;
import com.nhnacademy.team4.mindooray.dto.response.project.ProjectAccountIdResponse;
import com.nhnacademy.team4.mindooray.dto.response.project.ProjectMemberResponse;
import com.nhnacademy.team4.mindooray.dto.response.project.ProjectResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectAdapter projectAdapter;

    public String getProjectMemberRole(long accountId, long projectId) {
        ProjectMemberResponse projectMemberResponse = projectAdapter.checkProjectAccount(accountId, projectId);
        return projectMemberResponse.getProjectRole();
    }

    public List<ProjectResponse> getProjects(long accountId) {
        return projectAdapter.getAccountProjects(accountId);
    }

    public void createProject(long accountId, CreateProjectRequest createProjectRequest) {
        createProjectRequest.setAccountId(accountId);
        projectAdapter.createProject(createProjectRequest);
    }

    public List<Long> getProjectAccountIds(Long projectId) {
        return projectAdapter.getProjectAccountIds(projectId).stream()
                .map(ProjectAccountIdResponse::getAccountId)
                .collect(Collectors.toList());
    }
}
