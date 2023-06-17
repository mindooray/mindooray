package com.nhnacademy.team4.mindooray.global.manager;

import com.nhnacademy.team4.mindooray.global.exception.RedisSessionAccountIdNotFoundException;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ExceptionFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (RedisSessionAccountIdNotFoundException e) {
            response.sendRedirect("/login");
        }
    }
}
