package com.nhnacademy.team4.mindooray.controller;

import com.nhnacademy.team4.mindooray.dto.request.CreateTagRequest;
import com.nhnacademy.team4.mindooray.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class TagController {
    private final TagService tagService;

    @PostMapping("/projects/{projectId}/tags")
    public String createTag(
            @PathVariable("projectId") Long projectId,
            CreateTagRequest createTagRequest
    ) {
        tagService.createTag(projectId, createTagRequest);
        return "redirect:/projects/" + projectId + "/tasks";
    }

    @PostMapping("projects/{projectId}/tags/{tagId}/delete")
    public String deleteTag(
            @PathVariable("projectId") Long projectId,
            @PathVariable("tagId") Long tagId
    ) {
        tagService.deleteTag(tagId);
        return "redirect:/projects/" + projectId + "/tasks";
    }
}