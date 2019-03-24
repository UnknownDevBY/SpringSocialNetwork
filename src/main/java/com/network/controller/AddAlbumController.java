package com.network.controller;

import com.network.model.User;
import com.network.service.AddAlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.NotBlank;

@Controller
public class AddAlbumController {

    @Autowired private AddAlbumService addAlbumService;

    @GetMapping("/albums/add")
    public String openAddPhoto(@AuthenticationPrincipal User currentUser,
                               Model model) {
        model.addAttribute("currentUser", currentUser);
        return "addAlbum";
    }

    @PostMapping("/albums/add")
    public String addPhoto(@NotBlank @RequestParam String title,
                           @AuthenticationPrincipal User currentUser) {
        addAlbumService.addAlbum(title, currentUser);
        return "redirect:/news";
    }
}
