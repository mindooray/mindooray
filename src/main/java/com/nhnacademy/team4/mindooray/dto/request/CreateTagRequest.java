package com.nhnacademy.team4.mindooray.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CreateTagRequest {
    private Long projectId;
    private String name;
}
