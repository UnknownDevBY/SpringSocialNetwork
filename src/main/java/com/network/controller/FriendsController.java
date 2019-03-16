package com.network.controller;

import com.network.dto.PrivacySettingsDto;
import com.network.model.PrivacySettings;
import com.network.model.User;
import com.network.repository.FriendshipRepository;
import com.network.repository.PrivacySettingsRepository;
import com.network.repository.UserRepository;
import com.network.service.FriendsService;
import com.network.service.UserPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
public class FriendsController {

    @Autowired private UserRepository userRepository;
    @Autowired private FriendshipRepository friendshipRepository;
    @Autowired private PrivacySettingsRepository privacySettingsRepository;
    @Autowired private UserPageService userPageService;
    @Autowired private FriendsService friendsService;

    @GetMapping("/friends/{id}")
    public String showFriends(@PathVariable int id,
                              @AuthenticationPrincipal User currentUser,
                              HttpServletRequest request,
                              Map<String, Object> model) {
        model.put("currentUser", currentUser);
        User pageUser = userRepository.getById(id);
        PrivacySettings privacySettings = privacySettingsRepository.getByUser(pageUser);
        if(pageUser == null && privacySettings.getFriends() != 'a') {
            String referer = request.getHeader("Referer");
            if(referer != null)
                return "redirect:" + referer;
            else return "redirect:/";
        }
        model.put("id", id);
        int currentUserId = currentUser.getId();
        boolean areFriends = userPageService.isFirstFriendToSecond(id, currentUserId) && userPageService.isFirstFriendToSecond(currentUserId, id);
        PrivacySettingsDto privacySettingsDto = userPageService.getPrivacySettings(currentUser, pageUser, areFriends);
        if(areFriendsAllowed(id, currentUser, privacySettings, privacySettingsDto)) {
            setModel(pageUser, model);
        } else {
            String referer = request.getHeader("Referer");
            return "redirect:"+ referer;
        }
        return "friends";
    }

    private void setModel(User pageUser, Map<String, Object> model) {
        model.put("friends", friendsService.setRelation(friendshipRepository.getAllFriends(pageUser.getId()), "friends"));
        model.put("subscribers", friendsService.setRelation(friendshipRepository.getAllSubscribers(pageUser.getId()), "subscribers"));
        model.put("subscriptions", friendsService.setRelation(friendshipRepository.getAllSubscriptions(pageUser.getId()), "subscriptions"));
    }

    private boolean areFriendsAllowed(int id, User currentUser, PrivacySettings privacySettings, PrivacySettingsDto privacySettingsDto) {
        return (currentUser != null && currentUser.getId() == id) || privacySettingsDto.isAreFriendsAllowed() || (privacySettings.getFriends() == null || privacySettings.getFriends() == 'a');
    }
}
