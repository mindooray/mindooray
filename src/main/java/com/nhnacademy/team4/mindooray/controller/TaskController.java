package com.nhnacademy.team4.mindooray.controller;

import com.nhnacademy.team4.mindooray.dto.CommentDTO;
import com.nhnacademy.team4.mindooray.dto.TaskDTO;
import com.nhnacademy.team4.mindooray.dto.request.CreateTaskRequest;
import com.nhnacademy.team4.mindooray.dto.response.CommentResponse;
import com.nhnacademy.team4.mindooray.dto.response.account.AccountResponse;
import com.nhnacademy.team4.mindooray.dto.response.project.ProjectTagResponse;
import com.nhnacademy.team4.mindooray.dto.task.TaskResponse;
import com.nhnacademy.team4.mindooray.repository.RedisRepository;
import com.nhnacademy.team4.mindooray.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/projects/{projectId}/tasks")
public class TaskController {
    private final TaskService taskService;
    private final TagService tagService;
    private final ProjectService projectService;
    private final AccountService accountService;
    private final CommentService commentService;
    private final RedisRepository redisRepository;

    /**
     * 프로젝트에 속한 전체 task 리스트
     *
     * @param projectId 프로젝트 식별번호
     * @param model
     * @return
     */
    @GetMapping
    public String getProject(
            @PathVariable("projectId") Long projectId,
            Model model
    ) {
        // 프로젝트 id
        model.addAttribute("projectId", projectId);
        // Task 리스트 가져오기
        List<TaskDTO> tasks = taskService.getTasks(projectId);
        model.addAttribute("taskList", tasks);

        // tag 리스트 가져오기
        List<ProjectTagResponse> tagList = tagService.getProjectTags(projectId);
        model.addAttribute("tagList", tagList);

        // project member 리스트 가져오기
        List<Long> projectAccountIdList = projectService.getProjectAccountIds(projectId);
        List<AccountResponse> accountList = accountService.getAccounts(projectAccountIdList);
        model.addAttribute("accountList", accountList);

        // 전체 멤버 가져오기
        List<AccountResponse> totalAccountList = accountService.getAccounts();
        model.addAttribute("totalAccount", totalAccountList);

        return "task";
    }

    /**
     * task 상세 정보
     * @param taskId
     * @param model
     * @return
     */
    @GetMapping("/{taskId}")
    public String getTasks(
            @PathVariable("projectId") Long projectId,
            @PathVariable("taskId") Long taskId,
            Model model
    ) {
        // task 정보 가져오기
        TaskDTO taskDTO= taskService.getTask(projectId, taskId);
        model.addAttribute("task", taskDTO);

        List<CommentDTO> commentList = commentService.getTaskComments(taskId);
        model.addAttribute("commentList", commentList);

        return "task_detail";
    }


    @GetMapping("/create")
    public String createTaskView(
            @PathVariable("projectId") Long projectId,
            Model model
    ) {
        List<ProjectTagResponse> tags = tagService.getProjectTags(projectId);
        model.addAttribute("tagList", tags);

        return "task-create";
    }

    @PostMapping("/create")
    public String createTask(
            @CookieValue("SESSION") String key,
            @PathVariable("projectId") Long projectId,
            @ModelAttribute CreateTaskRequest createTaskRequest
    ) {
        long accountId = redisRepository.getSessionAccountId(key);
        taskService.createTask(accountId, projectId, createTaskRequest);
        return "redirect:/projects/" + projectId + "/tasks";
    }
}
