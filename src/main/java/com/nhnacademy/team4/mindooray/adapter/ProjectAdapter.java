package com.nhnacademy.team4.mindooray.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class ProjectAdapter {
    private final RestTemplate restTemplate;

}
