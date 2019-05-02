package com.network.controller;

import com.network.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ActivationController {

    @Autowired private RegistrationService registrationService;

    @GetMapping("/activation/{activationCode}")
    public String activate(@PathVariable String activationCode,
                           Model model) {
        String name = registrationService.activateUser(activationCode);
        model.addAttribute("name", name);
        return "activation";
    }
}
