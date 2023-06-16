package com.nhnacademy.team4.mindooray.domain.task.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class TaskTagRequest {
    private List<Long> tagIds;
}
