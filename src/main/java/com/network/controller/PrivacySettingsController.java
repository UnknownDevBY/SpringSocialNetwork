package com.network.controller;

import com.network.model.PrivacySettings;
import com.network.model.User;
import com.network.repository.PrivacySettingsRepository;
import com.network.service.PrivacySettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.LinkedHashMap;
import java.util.Map;

@Controller
public class PrivacySettingsController {

    @Autowired private PrivacySettingsRepository privacySettingsRepository;
    @Autowired private PrivacySettingsService privacySettingsService;

    @GetMapping("/privacy-settings")
    public Map<String, Object> openPrivacySettings(@AuthenticationPrincipal User currentUser) {
        Map<String, Object> map = new LinkedHashMap<>();
        PrivacySettings privacySettings = privacySettingsRepository.getByUser(currentUser);
        map.put("currentUser", currentUser);
        map.put("curPrivSet", privacySettings);
        return map;
    }

    @PostMapping("/privacy-settings")
    @ResponseStatus(HttpStatus.OK)
    public void savePrivacySettings(@ModelAttribute PrivacySettings settings,
                                    @AuthenticationPrincipal User currentUser) {
        privacySettingsService.savePrivacySettings(settings, currentUser);
    }
}
