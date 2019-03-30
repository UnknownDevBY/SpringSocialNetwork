package com.network.controller;

import com.network.dto.PrivacySettingsDto;
import com.network.model.PrivacySettings;
import com.network.model.User;
import com.network.repository.PrivacySettingsRepository;
import com.network.repository.UserRepository;
import com.network.service.FriendService;
import com.network.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
public class FriendController {

    @Autowired private UserRepository userRepository;
    @Autowired private PrivacySettingsRepository privacySettingsRepository;
    @Autowired private UserService userService;
    @Autowired private FriendService friendService;

    @Value("${s3.bucket}")
    private String bucketName;

    @GetMapping("/friends/{id}")
    public String showFriends(@PathVariable int id,
                              @AuthenticationPrincipal User currentUser,
                              HttpServletRequest request,
                              Map<String, Object> model) {
        model.put("currentUser", currentUser);
        User pageUser = userRepository.getById(id);
        PrivacySettings privacySettings = privacySettingsRepository.getByUser(pageUser);
        if(pageUser == null && privacySettings.getFriends() != 'a')
            return "redirect:" + request.getHeader("Referer");
        boolean areFriends = userService.areFriends(currentUser.getId(), id);
        PrivacySettingsDto privacySettingsDto = userService.getPrivacySettings(currentUser, pageUser, areFriends);
        if(privacySettingsDto.isAreFriendsAllowed()) {
            model.put("id", id);
            model.put("bucketName", bucketName);
            model.put("friends", userService.getFriends(pageUser));
            model.put("subscribers", friendService.setRelation(friendService.getAllSubscribers(pageUser)));
            model.put("subscriptions", friendService.setRelation(friendService.getAllSubscriptions(pageUser)));
        } else return "redirect:" + request.getHeader("Referer");
        return "friends";
    }
}
