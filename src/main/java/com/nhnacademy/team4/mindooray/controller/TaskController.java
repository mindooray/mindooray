package com.nhnacademy.team4.mindooray.controller;

import com.nhnacademy.team4.mindooray.adapter.TaskAdapter;
import com.nhnacademy.team4.mindooray.dto.task.TaskResponse;
import com.nhnacademy.team4.mindooray.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @GetMapping("/projects/{projectId}/tasks")
    public String getTasks(
            @PathVariable("projectId") Long projectId,
            Model model
            ) {
        List<TaskResponse> taskResponseList = taskService.getTasks(projectId);
        model.addAttribute("taskList", taskResponseList);
        return "task";
    }
}
