package com.network.controller;

import com.network.model.User;
import com.network.service.EditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class EditController {

    @Autowired private EditService editService;

    @GetMapping("/edit")
    public Map<String, User> openEdit(@AuthenticationPrincipal User currentUser) {
        return Map.of("currentUser", currentUser);
    }

    @PostMapping("/edit")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody String saveEdit(@AuthenticationPrincipal User currentUser,
                           @ModelAttribute User user,
                           @RequestParam String pass) {
        return editService.saveEdit(currentUser, user, pass);
    }
}
