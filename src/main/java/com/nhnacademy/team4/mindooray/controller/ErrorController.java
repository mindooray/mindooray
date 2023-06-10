package com.nhnacademy.team4.mindooray.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorController {
    @GetMapping("/403")
    public String authorizationError() {
        return "403";
    }
}
