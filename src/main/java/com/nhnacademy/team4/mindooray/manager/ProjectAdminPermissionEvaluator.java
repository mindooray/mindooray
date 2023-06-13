package com.nhnacademy.team4.mindooray.manager;

import com.nhnacademy.team4.mindooray.adapter.ProjectAdapter;
import com.nhnacademy.team4.mindooray.dto.response.project.ProjectMemberResponse;
import com.nhnacademy.team4.mindooray.repository.RedisRepository;
import com.nhnacademy.team4.mindooray.utils.CookieUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

@Component
@RequiredArgsConstructor
public class ProjectAdminPermissionEvaluator implements PermissionEvaluator {
    private final HttpServletRequest request;
    private final RedisRepository redisRepository;
    private final ProjectAdapter projectAdapter;

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        Cookie cookie = CookieUtils.getCookie(request, "SESSION")
                .orElseThrow();
        Long accountId = redisRepository.getSessionAccountId(cookie.getValue());
        long projectId = Long.parseLong(String.valueOf(targetDomainObject));

        ProjectMemberResponse memberResponse = projectAdapter.checkProjectAccount(accountId, projectId);

        return memberResponse.getProjectRole().equals(permission);
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        return false;
    }
}
