package com.nhnacademy.team4.mindooray.controller;

import com.nhnacademy.team4.mindooray.dto.request.CreateTaskRequest;
import com.nhnacademy.team4.mindooray.dto.response.project.ProjectTagResponse;
import com.nhnacademy.team4.mindooray.dto.task.TaskResponse;
import com.nhnacademy.team4.mindooray.repository.RedisRepository;
import com.nhnacademy.team4.mindooray.service.TagService;
import com.nhnacademy.team4.mindooray.service.TaskService;
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
        // Task 리스트 가져오기
        List<TaskResponse> tasks = taskService.getTasks(projectId);
        model.addAttribute("taskList", tasks);

        // project member 리스트 가져오기

        return "task";
    }

    /**
     * @param taskId
     * @param model
     * @return
     */
    @GetMapping("/{taskId}")
    public String getTasks(
            @PathVariable("taskId") Long taskId,
            Model model
    ) {
        TaskResponse taskResponse = taskService.getTask(taskId);
        model.addAttribute("task", taskResponse);
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
