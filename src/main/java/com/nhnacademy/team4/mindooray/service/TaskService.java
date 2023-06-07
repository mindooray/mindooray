package com.nhnacademy.team4.mindooray.service;

import com.nhnacademy.team4.mindooray.adapter.TaskAdapter;
import com.nhnacademy.team4.mindooray.dto.task.TaskResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskAdapter taskAdapter;

    public List<TaskResponse> getTasks(long projectId) {
        return taskAdapter.getTasks(projectId);
    }
}