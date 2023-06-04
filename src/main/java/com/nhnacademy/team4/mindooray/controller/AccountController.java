package com.nhnacademy.team4.mindooray.controller;

import com.nhnacademy.team4.mindooray.domain.AccountDetails;
import com.nhnacademy.team4.mindooray.dto.request.CreateAccountRequest;
import com.nhnacademy.team4.mindooray.dto.response.AccountResponse;
import com.nhnacademy.team4.mindooray.service.AccountService;
import com.nhnacademy.team4.mindooray.utils.CookieUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequiredArgsConstructor
public class AccountController {
    private static final String SESSION = "SESSION";
    private final AccountService accountService;

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/register")
    public String register(
            CreateAccountRequest createAccountRequest,
            Model model
    ) {
        AccountResponse accountResponse = accountService.register(createAccountRequest);

        return "redirect:/login";
    }
}
