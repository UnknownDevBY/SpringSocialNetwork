package com.network.controller;

import com.network.dto.UserDto;
import com.network.model.User;
import com.network.repository.PhotoRepository;
import com.network.repository.UserRepository;
import com.network.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
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
    public String showUser(@PathVariable int id,
                           @AuthenticationPrincipal User currentUser,
                           Map<String, Object> model) {
        User pageUser = userRepository.getById(id);
        if(!userRepository.existsById(id)) {
            return "redirect:/";
        }
        boolean is1friendTo2 = currentUser != null && userService.isFirstFriendToSecond(currentUser.getId(), id);
        boolean is2friendTo1 = currentUser != null && userService.isFirstFriendToSecond(id, currentUser.getId());
        boolean areFriends = is1friendTo2 && is2friendTo1;
        List<UserDto> friends = userService.getFriends(pageUser);
        model.put("friends", friends);
        model.put("displayFriends", friends.size() < 6 ? friends.size() % 6 : 6);
        model.put("is1friendTo2", is1friendTo2);
        model.put("is2friendTo1", is2friendTo1);
        model.put("currentUser", currentUser);
        model.put("pageUser", pageUser);
        model.put("avatar", photoRepository.getAvatarByUserId(id));
        model.put("allPhotos", userService.getPhotos(pageUser, currentUser));
        model.put("posts", userService.getPosts(pageUser, currentUser));
        model.put("privacySettings", userService.getPrivacySettings(currentUser, pageUser, areFriends));
        model.put("bucketName", bucketName);
        return "user";
    }

    @GetMapping("/users/friendship/{id}")
    public String addFriend(@PathVariable int id,
                            HttpServletRequest request,
                            @AuthenticationPrincipal User currentUser) {
        User pageUser = userRepository.getById(id);
        userService.modifyRelation(currentUser, pageUser);
        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }

    @PostMapping("/users/{id}")
    public String addPost(@PathVariable int id,
                          @AuthenticationPrincipal User currentUser,
                          @RequestParam String content,
                          Map<String, Object> model) {
        userService.savePost(id, content, currentUser, null);
        return showUser(id, currentUser, model);
    }
}
