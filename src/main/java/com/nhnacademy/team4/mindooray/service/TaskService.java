package com.nhnacademy.team4.mindooray.service;

import com.nhnacademy.team4.mindooray.adapter.AccountAdapter;
import com.nhnacademy.team4.mindooray.adapter.TaskAdapter;
import com.nhnacademy.team4.mindooray.dto.TaskDTO;
import com.nhnacademy.team4.mindooray.dto.request.CreateTaskRequest;
import com.nhnacademy.team4.mindooray.dto.response.account.AccountResponse;
import com.nhnacademy.team4.mindooray.dto.task.TaskResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.expression.Lists;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskAdapter taskAdapter;
    private final AccountAdapter accountAdapter;

    public List<TaskDTO> getTasks(long projectId) {
        List<TaskResponse> taskResponseList = taskAdapter.getTasks(projectId);
        List<Long> accountIds = taskResponseList.stream()
                .map(TaskResponse::getAccountId)
                .collect(Collectors.toList());
        List<AccountResponse> accountResponseList = accountAdapter.getAccounts(accountIds);

        return taskResponseList.stream()
                .map(t -> {
                    for (AccountResponse accountResponse : accountResponseList) {
                        if (t.getAccountId() == accountResponse.getAccountId()) {
                            return new TaskDTO(t, accountResponse);
                        }
                    }
                    throw new RuntimeException();
                })
                .collect(Collectors.toList());
    }

    public TaskDTO getTask(long projectId, long taskId) {
        TaskResponse task = taskAdapter.getTask(projectId, taskId);
        AccountResponse account = accountAdapter.getAccountById(task.getAccountId());

        return new TaskDTO(task, account);
    }

    public void createTask(long accountId, Long projectId, CreateTaskRequest createTaskRequest) {
        createTaskRequest.setAccountId(accountId);
        taskAdapter.createTask(projectId, createTaskRequest);
    }
}
