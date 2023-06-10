package com.nhnacademy.team4.mindooray.controller;

import com.nhnacademy.team4.mindooray.dto.request.CreateAccountRequest;
import com.nhnacademy.team4.mindooray.dto.request.UpdateAccountRequest;
import com.nhnacademy.team4.mindooray.dto.response.account.AccountResponse;
import com.nhnacademy.team4.mindooray.repository.RedisRepository;
import com.nhnacademy.team4.mindooray.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;
    private final RedisRepository redisRepository;

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
            CreateAccountRequest createAccountRequest
    ) {
        accountService.register(createAccountRequest);

        return "redirect:/login";
    }

    @GetMapping("/account/update")
    public String updateAccountView(
            @CookieValue("SESSION") String key,
            Model model
    ) {
        long accountId = redisRepository.getSessionAccountId(key);
        AccountResponse accountResponse = accountService.getAccount(accountId);
        model.addAttribute("account", accountResponse);
        return "account-update";
    }

    @PostMapping("/account/update")
    public String update(
            @CookieValue("SESSION") String key,
            @ModelAttribute UpdateAccountRequest updateAccountRequest
    ) {
        long accountId = redisRepository.getSessionAccountId(key);
        accountService.updateAccount(accountId, updateAccountRequest);
        return "redirect:/account";
    }

    @GetMapping("/account")
    public String myPage(
            @CookieValue("SESSION") String key,
            Model model
    ) {
        long accountId = redisRepository.getSessionAccountId(key);
        AccountResponse accountResponse = accountService.getAccount(accountId);
        model.addAttribute("account", accountResponse);
        return "mypage";
    }
}
