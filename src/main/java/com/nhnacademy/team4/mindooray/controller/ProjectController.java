package com.nhnacademy.team4.mindooray.controller;

import com.nhnacademy.team4.mindooray.dto.response.project.ProjectResponse;
import com.nhnacademy.team4.mindooray.dto.task.TaskResponse;
import com.nhnacademy.team4.mindooray.repository.RedisRepository;
import com.nhnacademy.team4.mindooray.service.ProjectService;
import com.nhnacademy.team4.mindooray.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;
    private final TaskService taskService;
    private final RedisRepository redisRepository;

    @GetMapping("/projects")
    public String getProjects(
            @CookieValue("SESSION") String key,
            Model model
    ) {
        long accountId = redisRepository.getSessionAccountId(key);
        List<ProjectResponse> projectResponseList = projectService.getProjects(accountId);
        model.addAttribute("projectList", projectResponseList);
        return "project";
    }

    @GetMapping("/projects/{projectId}")
    public String getProject(
            @PathVariable("projectId") Long projectId,
            Model model
    ) {
        List<TaskResponse> tasks = taskService.getTasks(projectId);
        model.addAttribute("taskList", tasks);
        return "task";
    }

    // hasAuthority -> USER
    @PostMapping("/projects")
    public String createProject(
            @CookieValue("SESSION") String key
    ) {
        return "project";
    }

}
