package com.nhnacademy.team4.mindooray.domain.project.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class ProjectResponse {
    private long projectId;
    private String title;
    private String description;
}
