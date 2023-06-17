package com.nhnacademy.team4.mindooray.domain.task.controller;

import com.nhnacademy.team4.mindooray.domain.account.service.AccountService;
import com.nhnacademy.team4.mindooray.domain.comment.service.CommentService;
import com.nhnacademy.team4.mindooray.domain.milestone.dto.MilestoneResponse;
import com.nhnacademy.team4.mindooray.domain.milestone.service.MilestoneService;
import com.nhnacademy.team4.mindooray.domain.project.service.ProjectService;
import com.nhnacademy.team4.mindooray.domain.tag.service.TagService;
import com.nhnacademy.team4.mindooray.domain.task.service.TaskService;
import com.nhnacademy.team4.mindooray.domain.comment.dto.CommentDTO;
import com.nhnacademy.team4.mindooray.domain.task.dto.TaskDTO;
import com.nhnacademy.team4.mindooray.domain.task.dto.CreateTaskRequest;
import com.nhnacademy.team4.mindooray.domain.account.dto.AccountResponse;
import com.nhnacademy.team4.mindooray.domain.tag.dto.TagResponse;
import com.nhnacademy.team4.mindooray.global.repository.RedisRepository;
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
    private final MilestoneService milestoneService;
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
        List<TagResponse> tagList = tagService.getProjectTags(projectId);
        model.addAttribute("tagList", tagList);

        // project member 리스트 가져오기
        List<Long> projectAccountIdList = projectService.getProjectAccountIds(projectId);
        List<AccountResponse> accountList = accountService.getAccounts(projectAccountIdList);
        model.addAttribute("accountList", accountList);

        // 속하지 않는 전체 멤버 가져오기
        List<AccountResponse> totalAccountList = accountService.getNoneProjectAccounts(projectAccountIdList);
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

        // task tag 정보 가져오기
        List<TagResponse> taskTagList = tagService.getTaskTags(taskId);
        model.addAttribute("taskTagList", taskTagList);

        // task milestone 정보 가져오기
        MilestoneResponse milestone = milestoneService.getTaskMilestone(taskId);
        model.addAttribute("milestone", milestone);

        // project tag 정보 가져오기
        List<TagResponse> tagList = tagService.getProjectTags(projectId);
        model.addAttribute("tagList", tagList);

        // 댓글 가져오기
        List<CommentDTO> commentList = commentService.getTaskComments(taskId);
        model.addAttribute("commentList", commentList);

        return "task_detail";
    }


    @GetMapping("/create")
    public String createTaskView(
            @PathVariable("projectId") Long projectId,
            Model model
    ) {
        List<TagResponse> tags = tagService.getProjectTags(projectId);
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
