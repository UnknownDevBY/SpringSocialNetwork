package com.network.controller;

import com.network.dto.UserDto;
import com.network.model.Community;
import com.network.model.User;
import com.network.repository.CommunityRepository;
import com.network.repository.PhotoRepository;
import com.network.service.CommunityService;
import com.network.service.PhotoService;
import com.network.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.io.IOException;
import java.util.List;

@Controller
public class CommunityController {

    @Autowired private CommunityService communityService;
    @Autowired private CommunityRepository communityRepository;
    @Autowired private PhotoRepository photoRepository;
    @Autowired private PhotoService photoService;
    @Autowired private UserService userService;

    @Value("${s3.bucket}")
    private String bucketName;

    @GetMapping("/communities")
    public String show(@RequestParam(required = false) String value,
                       @AuthenticationPrincipal User currentUser,
                       Model model) {
        List<Community> myCommunities = communityService.findCommunitiesByValueAndCurrentUser(value, currentUser);
        List<Community> allCommunities = communityService.findCommunitiesByValue(value, currentUser);
        allCommunities.removeAll(myCommunities);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("myCommunities", myCommunities);
        model.addAttribute("allCommunities", allCommunities);
        return "myGroups";
    }

    @GetMapping("/communities/public/{id}")
    public String showPublic(@PathVariable int id,
                             @AuthenticationPrincipal User currentUser,
                             HttpServletRequest request,
                             Model model) {
        if(!communityRepository.existsById(id))
            return "redirect:" + request.getHeader("Referer");
        Community community = communityRepository.getById(id);
        List<UserDto> subscribers = communityService.getSubscribers(community);
        model.addAttribute("community", community);
        model.addAttribute("posts", communityService.getPosts(community, currentUser));
        model.addAttribute("avatar", photoRepository.getAllByCommunityAndWasAvatarTrue(community));
        model.addAttribute("subscribers", subscribers.size() <= 6 ? subscribers : subscribers.subList(0, 6));
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("isSubscribed", community.getSubscribers().contains(currentUser));
        model.addAttribute("bucketName", bucketName);
        return "community";
    }

    @GetMapping("/communities/create")
    public String openCreateCommunity() {
        return "createGroup";
    }

    @GetMapping("/communities/public/{communityId}/subscribe")
    public String subscribe(@AuthenticationPrincipal User currentUser,
                            @PathVariable int communityId,
                            HttpServletRequest request) {
        communityService.updateSubscription(communityId, currentUser);
        return "redirect:" + request.getHeader("Referer");
    }

    @PostMapping("/communities/create")
    public String createCommunity(@ModelAttribute Community community,
                       @RequestParam MultipartFile avatar,
                       @AuthenticationPrincipal User currentUser) throws IOException {
        communityService.createCommunity(community, currentUser);
        photoService.savePhoto(true, avatar, null, community, null);
        return "redirect:/communities";
    }

    @PostMapping("/communities/public/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void createPost(@PathVariable int id,
                           @AuthenticationPrincipal User currentUser,
                           @Valid @RequestParam @NotBlank String content) {
        userService.savePost(id, content, currentUser, communityRepository.getById(id));
    }
}
