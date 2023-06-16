package com.nhnacademy.team4.mindooray.domain.tag.controller;

import com.nhnacademy.team4.mindooray.domain.tag.dto.TagRequest;
import com.nhnacademy.team4.mindooray.domain.tag.dto.TagResponse;
import com.nhnacademy.team4.mindooray.domain.tag.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class TagController {
    private final TagService tagService;

    @GetMapping("/projects/{projectId}/tags")
    public String getTags(
            @PathVariable("projectId") Long projectId,
            Model model
    ) {
        List<TagResponse> tagList = tagService.getProjectTags(projectId);
        model.addAttribute("tagList", tagList);

        return "tag-create";
    }

    @PostMapping("/projects/{projectId}/tags")
    public String createTag(
            @PathVariable("projectId") Long projectId,
            TagRequest tagRequest
    ) {
        tagService.createTag(projectId, tagRequest);
        return "redirect:/projects/" + projectId + "/tags";
    }

    @PostMapping("/projects/{projectId}/tags/{tagId}/update")
    public String updateTag(
            @PathVariable("projectId") Long projectId,
            @PathVariable("tagId") Long tagId,
            TagRequest tagRequest
    ) {
        tagService.updateTag(tagId, tagRequest);
        return "redirect:/projects/" + projectId + "/tags";
    }

    @GetMapping("projects/{projectId}/tags/{tagId}/delete")
    public String deleteTag(
            @PathVariable("projectId") Long projectId,
            @PathVariable("tagId") Long tagId
    ) {
        tagService.deleteTag(tagId);
        return "redirect:/projects/" + projectId + "/tags";
    }

    @PostMapping("/projects/{projectId}/tasks/{taskId}/tags")
    public String addTaskTag(
            @PathVariable("projectId") Long projectId,
            @PathVariable("taskId") Long taskId,
            @RequestParam List<Long> tagIds
    ) {
        tagService.addTaskTag(taskId, tagIds);
        return "redirect:/projects/" + projectId + "/tasks/" + taskId;
    }
}