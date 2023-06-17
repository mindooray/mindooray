package com.nhnacademy.team4.mindooray.domain.tag.service;

import com.nhnacademy.team4.mindooray.domain.tag.adapter.TagAdapter;
import com.nhnacademy.team4.mindooray.domain.task.dto.TaskTagRequest;
import com.nhnacademy.team4.mindooray.domain.tag.dto.TagRequest;
import com.nhnacademy.team4.mindooray.domain.tag.dto.TagResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagService {
    private final TagAdapter tagAdapter;

    public List<TagResponse> getProjectTags(long projectId) {
        return tagAdapter.getProjectTags(projectId);
    }

    public void createTag(Long projectId, TagRequest tagRequest) {
        tagAdapter.createTag(projectId, tagRequest);
    }

    public void deleteTag(Long tagId) {
        tagAdapter.deleteTag(tagId);
    }

    public List<TagResponse> getTaskTags(Long taskId) {
        return tagAdapter.getTaskTags(taskId);
    }

    public void updateTag(Long tagId, TagRequest tagRequest) {
        tagAdapter.updateTag(tagId, tagRequest);
    }

    public void addTaskTag(Long taskId, List<Long> tagIds) {
        TaskTagRequest taskTagRequest = new TaskTagRequest(tagIds);
        tagAdapter.addTaskTags(taskId, taskTagRequest);
    }
}
