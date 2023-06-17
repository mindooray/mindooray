package com.nhnacademy.team4.mindooray.domain.project.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CreateProjectAccountRequest {
    private List<Long> accountIds;
}
