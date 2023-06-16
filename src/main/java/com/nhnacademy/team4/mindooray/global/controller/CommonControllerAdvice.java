package com.nhnacademy.team4.mindooray.global.controller;

import com.nhnacademy.team4.mindooray.domain.account.domain.AccountDetails;
import com.nhnacademy.team4.mindooray.domain.project.dto.ProjectResponse;
import com.nhnacademy.team4.mindooray.global.exception.RedisSessionAccountIdNotFoundException;
import com.nhnacademy.team4.mindooray.global.repository.RedisRepository;
import com.nhnacademy.team4.mindooray.domain.project.service.ProjectService;
import com.nhnacademy.team4.mindooray.global.utils.CookieUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class CommonControllerAdvice {
    private final ProjectService projectService;
    private final RedisRepository redisRepository;

    @ModelAttribute
    public void accountProjects(
            @AuthenticationPrincipal AccountDetails accountDetails,
            HttpServletRequest request,
            Model model
    ) {
        if(Objects.isNull(accountDetails)) {
            return;
        }

        Cookie cookie = CookieUtils.getCookie(request, "SESSION")
                .orElseThrow();
        String value = cookie.getValue();

        Long accountId = redisRepository.getSessionAccountId(value);
        List<ProjectResponse> projects = projectService.getProjects(accountId);

        model.addAttribute("projectList", projects);
    }

    @ExceptionHandler(RedisSessionAccountIdNotFoundException.class)
    public String accountIdNotFound(HttpServletResponse response) throws IOException {
        return "redirect:/login";
    }
}
