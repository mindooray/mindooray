package com.nhnacademy.team4.mindooray.domain.task.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@Setter
@Getter
public class CreateTaskRequest {
    private Long accountId;
    private String title;
    private String content;
    private String milestone;
    private String milestoneName;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate milestoneStartDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate milestoneEndDate;
    private Boolean deadlineStatus;
    private List<Long> tags;
}
