package com.network.controller;

import com.network.model.Community;
import com.network.model.User;
import com.network.service.AddPhotoService;
import com.network.service.CreateCommunityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class CreateCommunityController {

    @Autowired private AddPhotoService addPhotoService;
    @Autowired private CreateCommunityService createCommunityService;

    @GetMapping("/communities/create")
    public String open() {
        return "createGroup";
    }

    @PostMapping("/communities/create")
    public String open(@ModelAttribute Community community,
                       @RequestParam MultipartFile avatar,
                       @AuthenticationPrincipal User currentUser) throws IOException {
        addPhotoService.savePhoto(true, avatar, null, community, null);
        createCommunityService.createCommunity(community, currentUser);
        return "redirect:/communities";
    }
}
