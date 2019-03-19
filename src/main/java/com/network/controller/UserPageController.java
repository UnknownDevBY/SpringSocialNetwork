package com.network.controller;

import com.network.dto.UserDto;
import com.network.model.User;
import com.network.repository.PhotoRepository;
import com.network.repository.UserRepository;
import com.network.service.UserPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
public class UserPageController {

    @Autowired private UserRepository userRepository;
    @Autowired private PhotoRepository photoRepository;
    @Autowired private UserPageService userPageService;

    @Value("${s3.bucket}")
    private String bucketName;

    @GetMapping("/users/{id}")
    public String showUser(@PathVariable int id,
                           @AuthenticationPrincipal User currentUser,
                           Map<String, Object> model) {
        User pageUser = userRepository.getById(id);
        if(userRepository.getById(id) == null) {
            return "redirect:/";
        }
        boolean is1friendTo2 = currentUser != null && userPageService.isFirstFriendToSecond(currentUser.getId(), id);
        boolean is2friendTo1 = currentUser != null && userPageService.isFirstFriendToSecond(id, currentUser.getId());
        boolean areFriends = is1friendTo2 && is2friendTo1;
        List<UserDto> friends = userPageService.getFriends(pageUser);
        model.put("friends", friends);
        model.put("displayFriends", friends.size() < 6 ? friends.size() % 6 : 6);
        model.put("is1friendTo2", is1friendTo2);
        model.put("is2friendTo1", is2friendTo1);
        model.put("currentUser", currentUser);
        model.put("pageUser", pageUser);
        model.put("avatar", photoRepository.getAvatarByUserId(id));
        model.put("allPhotos", photoRepository.getByUserId(id));
        model.put("posts", userPageService.getPosts(pageUser, currentUser));
        model.put("privacySettings", userPageService.getPrivacySettings(currentUser, pageUser, areFriends));
        model.put("bucketName", bucketName);
        return "user";
    }

    @PostMapping("/users/{id}")
    public String addPost(@PathVariable int id,
                          @AuthenticationPrincipal User currentUser,
                          @RequestParam String content,
                          Map<String, Object> model) {
        userPageService.savePost(id, content, currentUser);
        return showUser(id, currentUser, model);
    }
}
