package com.network.controller;

import com.network.model.User;
import com.network.repository.PhotoAlbumRepository;
import com.network.service.AddPhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class AddPhotoController {

    @Autowired private AddPhotoService addPhotoService;
    @Autowired private PhotoAlbumRepository albumRepository;

    @GetMapping("/photos/add")
    public String openAddPhoto(@AuthenticationPrincipal User currentUser,
                               Model model) {
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("albums", albumRepository.getAllTitlesByUserId(currentUser.getId()));
        return "addPhoto";
    }

    @PostMapping("/photos/add")
    public String addPhoto(@RequestParam(required = false) Boolean makeAvatar,
                           @RequestParam MultipartFile newPhoto,
                           @RequestParam(required = false) String album,
                           @AuthenticationPrincipal User currentUser) throws IOException {
        addPhotoService.savePhoto(makeAvatar, newPhoto, currentUser, null, album);
        return "redirect:/users/" + currentUser.getId();
    }
}
