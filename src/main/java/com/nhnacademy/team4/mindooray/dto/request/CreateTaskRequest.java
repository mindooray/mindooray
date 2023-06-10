package com.nhnacademy.team4.mindooray.dto.request;

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
    private String title;
    private String content;
    private String milestone;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate milestoneStartDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate milestoneEndDate;
    private List<Long> tags;
}
