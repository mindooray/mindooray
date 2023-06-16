package com.nhnacademy.team4.mindooray.domain.milestone.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
public class MilestoneResponse {

    private String name;

    private LocalDate startDate;

    private LocalDate finishDate;

    private boolean deadlineStatus;
}
