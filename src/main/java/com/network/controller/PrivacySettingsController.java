package com.network.controller;

import com.network.model.PrivacySettings;
import com.network.model.User;
import com.network.repository.PrivacySettingsRepository;
import com.network.service.PrivacySettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PrivacySettingsController {

    @Autowired private PrivacySettingsRepository privacySettingsRepository;
    @Autowired private PrivacySettingsService privacySettingsService;

    @GetMapping("/privacy-settings")
    public String openPrivacySettings(@AuthenticationPrincipal User currentUser,
                                      Model model) {
        PrivacySettings privacySettings = privacySettingsRepository.getByUser(currentUser);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("curPrivSet", privacySettings);
        return "privacySettings";
    }

    @PostMapping("/privacy-settings")
    public String savePrivacySettings(@ModelAttribute PrivacySettings settings,
                                      @AuthenticationPrincipal User currentUser) {
        privacySettingsService.savePrivacySettings(settings, currentUser);
        return "redirect:/users/" + currentUser.getId();
    }
}
