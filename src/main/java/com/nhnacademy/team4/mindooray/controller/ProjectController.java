package com.nhnacademy.team4.mindooray.controller;

import com.nhnacademy.team4.mindooray.dto.response.project.ProjectResponse;
import com.nhnacademy.team4.mindooray.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;

    @GetMapping("/projects")
    public String getProjects() {
        long accountId = 1;
        projectService.getProjects(accountId);
        return null;
    }

    @GetMapping("/projects/{projectId}")
    public String getProject(
            @PathVariable("projectId") Long projectId,
            Model model
    ) {
        long accountId = 1;
        ProjectResponse projectResponse = projectService.getProject(accountId, projectId);
        model.addAttribute("project", projectResponse);
        return "project";
    }

    // hasAuthority -> USER
    @PostMapping("/projects")
    public String createProject() {
        return null;
    }

}
