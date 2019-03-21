package com.network.controller;

import com.network.dto.UserDto;
import com.network.model.Community;
import com.network.model.User;
import com.network.repository.CommunityRepository;
import com.network.repository.PhotoRepository;
import com.network.service.CommunitiesService;
import com.network.service.UserPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class CommunitiesController {

    @Autowired private CommunitiesService communitiesService;
    @Autowired private CommunityRepository communityRepository;
    @Autowired private PhotoRepository photoRepository;
    @Autowired private UserPageService userPageService;

    @Value("${s3.bucket}")
    private String bucketName;

    @GetMapping("/communities")
    public String show(@RequestParam(required = false) String value,
                       @AuthenticationPrincipal User currentUser,
                       Model model) {
        List<Community> myCommunities = communitiesService.findCommunitiesByValueAndCurrentUser(value, currentUser);
        List<Community> allCommunities = communitiesService.findCommunitiesByValue(value, currentUser);
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
        List<UserDto> subscribers = communitiesService.getSubscribers(community);
        model.addAttribute("community", community);
        model.addAttribute("posts", communitiesService.getPosts(community, currentUser));
        model.addAttribute("avatar", photoRepository.getAllByCommunityAndWasAvatarTrue(community));
        model.addAttribute("subscribers", subscribers.size() <= 6 ? subscribers : subscribers.subList(0, 6));
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("isSubscribed", community.getSubscribers().contains(currentUser));
        model.addAttribute("bucketName", bucketName);
        return "community";
    }

    @PostMapping("/communities/public/{id}")
    public String createPost(@PathVariable int id,
                             @AuthenticationPrincipal User currentUser,
                             @RequestParam String content,
                             HttpServletRequest request,
                             Model model) {
        userPageService.savePost(id, content, null, communityRepository.getById(id));
        return showPublic(id, currentUser, request, model);
    }
}
