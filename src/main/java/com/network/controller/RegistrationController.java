package com.network.controller;

import com.network.aspect.annotation.Registration;
import com.network.model.User;
import com.network.repository.UserRepository;
import com.network.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.sql.Date;

@Controller
public class RegistrationController {

    @Autowired private RegistrationService registrationService;

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @Registration
    @PostMapping("/registration")
    public String addUser(@Valid @ModelAttribute User user,
                          @RequestParam String pass,
                          @RequestParam MultipartFile avatar,
                          Model model) throws IOException {
        String error = registrationService.getError(user);
        if(error != null) {
            model.addAttribute("error", error);
            return "registration";
        }
        registrationService.saveUser(user, pass, avatar);
        return "redirect:/";
    }
}
