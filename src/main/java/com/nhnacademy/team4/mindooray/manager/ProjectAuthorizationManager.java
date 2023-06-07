package com.nhnacademy.team4.mindooray.manager;

import com.nhnacademy.team4.mindooray.adapter.ProjectAdapter;
import com.nhnacademy.team4.mindooray.dto.response.project.ProjectMemberResponse;
import com.nhnacademy.team4.mindooray.repository.RedisRepository;
import com.nhnacademy.team4.mindooray.utils.BeanUtils;
import com.nhnacademy.team4.mindooray.utils.CookieUtils;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.function.Supplier;

public class ProjectAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {
    private static final SimpleGrantedAuthority ROLE_USER = new SimpleGrantedAuthority("ROLE_USER");
    private static final SimpleGrantedAuthority ROLE_ADMIN = new SimpleGrantedAuthority("ROLE_ADMIN");
    private static final String PROJECT_ADMIN = "PROJECT_ADMIN";
    private final String role;
    public ProjectAuthorizationManager(String role) {
        this.role = role;
    }
    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext object) {
        if(!hasAuthority(authentication.get())) {
            return new AuthorizationDecision(false);
        }

        HttpServletRequest request = object.getRequest();
        Cookie cookie = CookieUtils.getCookie(request, "SESSION")
                .orElseThrow();
        String sessionId = cookie.getValue();

        long projectId = Long.parseLong(object.getVariables().get("projectId"));
        return new AuthorizationDecision(checkProjectAuthority(sessionId, projectId));
    }

    private boolean hasAuthority(Authentication authentication) {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        return authorities.contains(ROLE_USER) || authorities.contains(ROLE_ADMIN);
    }

    private boolean checkProjectAuthority(String sessionId, long projectId) {
        RedisRepository redisRepository = BeanUtils.getBean(RedisRepository.class);
        ProjectAdapter projectAdapter = BeanUtils.getBean(ProjectAdapter.class);

        Long accountId = redisRepository.getSessionAccountId(sessionId, "accountId");

        ProjectMemberResponse memberResponse = projectAdapter.checkProjectAccount(accountId, projectId);
        String projectRole = memberResponse.getProjectRole();
        if(projectRole.equals(PROJECT_ADMIN)) {
            return true;
        }

        return projectRole.equals(role);
    }
}
