package com.nhnacademy.team4.mindooray.service;

import com.nhnacademy.team4.mindooray.adapter.ProjectAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectAdapter projectAdapter;

}
