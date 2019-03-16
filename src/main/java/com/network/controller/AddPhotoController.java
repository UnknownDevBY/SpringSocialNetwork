package com.network.controller;

import com.network.model.User;
import com.network.service.AddPhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class AddPhotoController {

    @Autowired private AddPhotoService addPhotoService;

    @GetMapping("/add-photo")
    public String openAddPhoto() {
        return "addPhoto";
    }

    @PostMapping("/add-photo")
    public String addPhoto(@RequestParam(required = false) Boolean makeAvatar,
                           @RequestParam MultipartFile newPhoto,
                           @AuthenticationPrincipal User currentUser) throws IOException {
        addPhotoService.savePhoto(makeAvatar, newPhoto, currentUser);
        return "redirect:/users/" + currentUser.getId();
    }
}
