package com.nhnacademy.team4.mindooray.domain.task.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class TaskResponse {
    private long taskId;
    private long accountId;
    private String projectName;
    private String title;
    private String content;
    private String status;
    private LocalDateTime createDate;
}
