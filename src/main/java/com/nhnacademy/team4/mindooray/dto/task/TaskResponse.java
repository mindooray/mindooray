package com.nhnacademy.team4.mindooray.dto.task;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class TaskResponse {
    private long id;
    private String title;
    private String projectTitle;
    private int number;
    private String representative;
    private String status;
}
