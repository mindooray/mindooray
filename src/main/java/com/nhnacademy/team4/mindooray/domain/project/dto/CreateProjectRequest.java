package com.nhnacademy.team4.mindooray.domain.project.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CreateProjectRequest {
    private Long accountId;
    private String title;
    private String description;
}
