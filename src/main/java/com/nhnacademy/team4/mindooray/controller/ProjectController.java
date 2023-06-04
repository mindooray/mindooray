package com.nhnacademy.team4.mindooray.controller;

import com.nhnacademy.team4.mindooray.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;
}
