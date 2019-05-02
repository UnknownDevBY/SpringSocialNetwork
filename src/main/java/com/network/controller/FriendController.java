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

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
public class FriendController {

    @Autowired private UserRepository userRepository;
    @Autowired private UserService userService;
    @Autowired private FriendService friendService;

    @GetMapping("/friends/{id}")
    public String showFriends(@PathVariable int id,
                              @AuthenticationPrincipal User currentUser,
                              HttpServletRequest request,
                              Map<String, Object> model) {
        User pageUser = userRepository.getById(id);
        if(pageUser == null || !friendService.areFriendsAllowed(pageUser, currentUser))
            return "redirect:" + request.getHeader("Referer");
        model.put("currentUser", currentUser);
        model.put("id", id);
        model.put("friends", userService.getFriends(pageUser));
        model.put("subscribers", friendService.setRelation(friendService.getAllSubscribers(pageUser)));
        model.put("subscriptions", friendService.setRelation(friendService.getAllSubscriptions(pageUser)));
        return "friends-list";
    }
}
