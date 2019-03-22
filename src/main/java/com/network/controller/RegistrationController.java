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

import java.io.IOException;
import java.sql.Date;

@Controller
public class RegistrationController {

    @Autowired private UserRepository userRepository;
    @Autowired private RegistrationService registrationService;

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @Registration
    @PostMapping("/registration")
    public String addUser(@ModelAttribute User user,
                          @RequestParam String pass,
                          @RequestParam MultipartFile avatar,
                          Model model) throws IOException {
        if(System.currentTimeMillis() - Date.valueOf(user.getDateOfBirth()).getTime() < 441_504_000_000L) {
            model.addAttribute("error", "Вы должны быть старше 14-и лет");
            return "registration";
        }
        if(userRepository.getByEmail(user.getEmail()) != null) {
            model.addAttribute("error", "Пользователь с таким логином уже существует");
            return "registration";
        }
        registrationService.saveUser(user, pass, avatar);
        return "redirect:/";
    }
}
