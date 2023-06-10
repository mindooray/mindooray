package com.nhnacademy.team4.mindooray.service;

import com.nhnacademy.team4.mindooray.adapter.ProjectAdapter;
import com.nhnacademy.team4.mindooray.dto.request.CreateProjectRequest;
import com.nhnacademy.team4.mindooray.dto.response.project.ProjectMemberResponse;
import com.nhnacademy.team4.mindooray.dto.response.project.ProjectResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public ProjectResponse getProject(long projectId) {
        return projectAdapter.getAccountProject(projectId);
    }

    public void createProject(long accountId, CreateProjectRequest createProjectRequest) {
        projectAdapter.createProject(accountId, createProjectRequest);
    }
}
