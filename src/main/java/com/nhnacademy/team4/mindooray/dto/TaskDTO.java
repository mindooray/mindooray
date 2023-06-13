package com.nhnacademy.team4.mindooray.dto;

import com.nhnacademy.team4.mindooray.dto.response.account.AccountResponse;
import com.nhnacademy.team4.mindooray.dto.task.TaskResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class TaskDTO {
    private long taskId;
    private Long accountId;
    private String accountName;
    private String projectName;
    private String title;
    private String content;
    private String status;
    private LocalDateTime createDate;

    public TaskDTO(TaskResponse taskResponse, AccountResponse accountResponse) {
        this.taskId = taskResponse.getTaskId();
        this.accountId = accountResponse.getAccountId();
        this.accountName = accountResponse.getLoginId();
        this.projectName = taskResponse.getProjectName();
        this.title = taskResponse.getTitle();
        this.content = taskResponse.getContent();
        this.status = taskResponse.getStatus();
        this.createDate = taskResponse.getCreateDate();
    }
}

