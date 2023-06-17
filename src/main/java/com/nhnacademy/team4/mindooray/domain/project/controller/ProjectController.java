package com.nhnacademy.team4.mindooray.domain.project.controller;

import com.nhnacademy.team4.mindooray.domain.project.dto.CreateProjectRequest;
import com.nhnacademy.team4.mindooray.global.repository.RedisRepository;
import com.nhnacademy.team4.mindooray.domain.project.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;
    private final RedisRepository redisRepository;

    @GetMapping({"/", "/home"})
    public String index(
            @CookieValue("SESSION") String key
    ) {
        return "index";
    }

    @GetMapping("/projects/create")
    public String createProjectView() {
        return "project-create";
    }

    @GetMapping("/projects/{projectId}/accounts/{accountId}/delete")
    public String deleteProjectAccount(
            @PathVariable("projectId") Long projectId,
            @PathVariable("accountId") Long accountId
    ) {
        projectService.deleteProjectAccount(projectId, accountId);
        return "redirect:/projects/" + projectId + "/tasks";
    }

    /**
     * 프로젝트 어카운트 추가
     *
     * @param projectId 어카운트를 추가할 프로젝트
     * @return
     */
    @PostMapping("/projects/{projectId}/accounts")
    public String addProjectAccount(
            @PathVariable("projectId") Long projectId,
            @RequestParam("accounts") List<Long> accounts
    ) {
        projectService.addProjectAccounts(projectId, accounts);
        return "redirect:/projects/" + projectId + "/tasks";
    }

    /**
     * 프로젝트 생성
     *
     * @param key redis 에서 account id 를 가져오기 위한 key
     * @return project 리스트 페이지
     */
    @PostMapping("/projects")
    public String createProject(
            @CookieValue("SESSION") String key,
            @ModelAttribute CreateProjectRequest createProjectRequest
    ) {
        long accountId = redisRepository.getSessionAccountId(key);
        projectService.createProject(accountId, createProjectRequest);
        return "redirect:/";
    }

}
