package com.network.controller;

import com.network.model.User;
import com.network.service.EditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;

@Controller
public class EditController {

    @Autowired private EditService editService;
    @Autowired private PasswordEncoder passwordEncoder;

    @GetMapping("/edit")
    public String openEdit(@AuthenticationPrincipal User currentUser,
                           Model model) {
        model.addAttribute("currentUser", currentUser);
        return "edit";
    }

    @PostMapping("/edit")
    public String saveEdit(@AuthenticationPrincipal User currentUser,
                           @ModelAttribute User user,
                           @RequestParam String pass,
                           Model model) {
        if(pass != null && !pass.isEmpty()) {
            currentUser.setPassword(passwordEncoder.encode(pass));
        }
        if(user.getDateOfBirth() == null || System.currentTimeMillis() - Date.valueOf(user.getDateOfBirth()).getTime() < 441_504_000_000L) {
            model.addAttribute("error", "Вы должны быть старше 14-и лет");
            return "edit";
        }
        editService.saveEdit(currentUser, user);
        return "redirect:/users/" + currentUser.getId();
    }
}
