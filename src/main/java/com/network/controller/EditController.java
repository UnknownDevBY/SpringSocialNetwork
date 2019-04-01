package com.network.controller;

import com.network.model.User;
import com.network.service.EditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;

@Controller
public class EditController {

    @Autowired private EditService editService;

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
        if(!editService.ageConfirmed(user.getDateOfBirth())) {
            model.addAttribute("error", "Вы должны быть старше 14-и лет");
            return "edit";
        }
        editService.saveEdit(currentUser, user, pass);
        return "redirect:/users/" + currentUser.getId();
    }
}
