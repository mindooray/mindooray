package com.nhnacademy.team4.mindooray.controller;

import com.nhnacademy.team4.mindooray.adapter.AccountAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class IndexController {
    private final AccountAdapter accountAdapter;

    @GetMapping("/")
    public String index() {
        accountAdapter.getAccounts();
        return "index";
    }
}
