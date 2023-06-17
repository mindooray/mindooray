package com.nhnacademy.team4.mindooray.domain.milestone.service;

import com.nhnacademy.team4.mindooray.domain.milestone.adapter.MilestoneAdapter;
import com.nhnacademy.team4.mindooray.domain.milestone.dto.MilestoneResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MilestoneService {
    private final MilestoneAdapter milestoneAdapter;

    public MilestoneResponse getTaskMilestone(Long taskId) {
        try {
            return milestoneAdapter.getTaskMilestone(taskId);
        } catch (Exception e) {
            return new MilestoneResponse();
        }
    }
}
