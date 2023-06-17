package com.nhnacademy.team4.mindooray.domain.project.service;

import com.nhnacademy.team4.mindooray.domain.project.adapter.ProjectAdapter;
import com.nhnacademy.team4.mindooray.domain.project.dto.CreateProjectAccountRequest;
import com.nhnacademy.team4.mindooray.domain.project.dto.CreateProjectRequest;
import com.nhnacademy.team4.mindooray.domain.project.dto.ProjectAccountIdResponse;
import com.nhnacademy.team4.mindooray.domain.project.dto.ProjectResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectAdapter projectAdapter;

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

    public void addProjectAccounts(Long projectId, List<Long> accounts) {
        CreateProjectAccountRequest createProjectAccount = new CreateProjectAccountRequest(accounts);
        projectAdapter.addProjectAccount(projectId, createProjectAccount);
    }

    public void deleteProjectAccount(Long projectId, Long accountId) {
        projectAdapter.deleteProjectAccount(projectId, accountId);
    }
}
