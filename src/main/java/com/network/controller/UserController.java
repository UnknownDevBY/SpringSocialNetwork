package com.network.controller;

import com.network.dto.UserDto;
import com.network.model.User;
import com.network.repository.PhotoRepository;
import com.network.repository.UserRepository;
import com.network.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
public class UserController {

    @Autowired private UserRepository userRepository;
    @Autowired private PhotoRepository photoRepository;
    @Autowired private UserService userService;

    @Value("${s3.bucket}")
    private String bucketName;

    @GetMapping("/users/{id}")
    public Map<String, Object> showUser(@PathVariable int id,
                           @AuthenticationPrincipal User currentUser) {
        User pageUser = userRepository.getById(id);
        if(!userRepository.existsById(id)) {
            return null;
        }
        Map<String, Object> map = new LinkedHashMap<>();
        boolean is1friendTo2 = currentUser != null && userService.isFirstFriendToSecond(currentUser.getId(), id);
        boolean is2friendTo1 = currentUser != null && userService.isFirstFriendToSecond(id, currentUser.getId());
        boolean areFriends = is1friendTo2 && is2friendTo1;
        List<UserDto> friends = userService.getFriends(pageUser);
        map.put("friends", friends);
        map.put("displayFriends", friends.size() < 6 ? friends.size() % 6 : 6);
        map.put("is1friendTo2", is1friendTo2);
        map.put("is2friendTo1", is2friendTo1);
        map.put("currentUserId", currentUser != null ? currentUser.getId() : 0);
        map.put("pageUser", pageUser);
        map.put("avatar", photoRepository.getAvatarByUserId(id));
        map.put("allPhotos", userService.getPhotos(pageUser, currentUser));
        map.put("posts", userService.getPosts(pageUser, currentUser));
        map.put("privacySettings", userService.getPrivacySettings(currentUser, pageUser, areFriends));
        map.put("bucketName", bucketName);
        return map;
    }

    @GetMapping("/users/friendship/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void addFriend(@PathVariable int id,
                          @AuthenticationPrincipal User currentUser) {
        User pageUser = userRepository.getById(id);
        userService.modifyRelation(currentUser, pageUser);
    }

    @PostMapping("/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void addPost(@PathVariable int id,
                        @AuthenticationPrincipal User currentUser,
                        @RequestParam String content) {
        userService.savePost(id, content, currentUser, null);
    }
}
