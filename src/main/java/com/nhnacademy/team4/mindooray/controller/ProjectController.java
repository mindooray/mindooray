package com.nhnacademy.team4.mindooray.controller;

import com.nhnacademy.team4.mindooray.dto.request.CreateProjectRequest;
import com.nhnacademy.team4.mindooray.repository.RedisRepository;
import com.nhnacademy.team4.mindooray.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;
    private final RedisRepository redisRepository;

    /**
     * 프로젝트에 속한 어카운트 리스트
     *
     * @param projectId 프로젝트 아이디
     * @param model
     * @return
     */
    @GetMapping("/projects/{projectId}/accounts")
    public String getProjectAccounts(
            @PathVariable("projectId") Long projectId,
            Model model
    ) {
        return null;
    }


    /**
     * 프로젝트 어카운트 추가
     *
     * @param projectId 어카운트를 추가할 프로젝트
     * @param accountId 프로젝트에 추가될 어카운트 아이디
     * @return
     */
    @PostMapping("/projects/{projectId}/accounts/{accountId}")
    public String addProjectAccount(
            @PathVariable("projectId") Long projectId,
            @PathVariable("accountId") Long accountId
    ) {
        return null;
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
        return "redirect:/project";
    }

}
