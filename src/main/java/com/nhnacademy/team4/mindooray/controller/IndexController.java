package com.nhnacademy.team4.mindooray.controller;

import com.nhnacademy.team4.mindooray.dto.response.project.ProjectResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class IndexController {
    @GetMapping({"/", "/home"})
    public String index() {
        return "index";
    }
}
