package com.nhnacademy.team4.mindooray.controller;

import com.nhnacademy.team4.mindooray.adapter.TaskAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class TaskController {
    private final TaskAdapter taskAdapter;

    @GetMapping("/projects/{projectId}/tasks")
    public String getTasks() {
        return null;
    }
}
