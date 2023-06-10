package com.nhnacademy.team4.mindooray.service;

import com.nhnacademy.team4.mindooray.adapter.TagAdapter;
import com.nhnacademy.team4.mindooray.dto.response.project.ProjectTagResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagService {
    private final TagAdapter tagAdapter;

    public List<ProjectTagResponse> getProjectTags(long projectId) {
        return tagAdapter.getProjectTags(projectId);
    }

}
