package com.network.controller;

import com.network.model.User;
import com.network.repository.UserRepository;
import com.network.service.FriendService;
import com.network.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
public class FriendController {

    @Autowired private UserRepository userRepository;
    @Autowired private UserService userService;
    @Autowired private FriendService friendService;

    @Value("${s3.bucket}")
    private String bucketName;

    @GetMapping("/friends/{id}")
    public Map<String, Object> showFriends(@PathVariable int id,
                              @AuthenticationPrincipal User currentUser) {
        User pageUser = userRepository.getById(id);
        if(pageUser == null || !friendService.areFriendsAllowed(pageUser, currentUser))
            return null;
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("currentUserId", currentUser != null ? currentUser.getId() : 0);
        map.put("id", id);
        map.put("bucketName", bucketName);
        map.put("friends", userService.getFriends(pageUser));
        map.put("subscribers", friendService.setRelation(friendService.getAllSubscribers(pageUser)));
        map.put("subscriptions", friendService.setRelation(friendService.getAllSubscriptions(pageUser)));
        return map;
    }
}
