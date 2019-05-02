package com.network.controller;

import com.network.model.PrivacySettings;
import com.network.model.User;
import com.network.service.PrivacySettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PrivacySettingsController {

    @Autowired private PrivacySettingsService privacySettingsService;

    @PostMapping("/privacy-settings")
    public String savePrivacySettings(@ModelAttribute PrivacySettings settings,
                                      @AuthenticationPrincipal User currentUser) {
        privacySettingsService.savePrivacySettings(settings, currentUser);
        return "redirect:/users/" + currentUser.getId();
    }
}
