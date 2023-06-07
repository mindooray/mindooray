package com.nhnacademy.team4.mindooray.dto.response.project;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ProjectMemberResponse {
    @JsonProperty("project_role")
    private String projectRole;
}
