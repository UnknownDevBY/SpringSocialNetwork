package com.network.controller;

import com.network.dto.CommunityDto;
import com.network.dto.PostDto;
import com.network.model.Community;
import com.network.model.User;
import com.network.repository.CommunityRepository;
import com.network.repository.CommunitySubscriberRepository;
import com.network.repository.PhotoRepository;
import com.network.service.CommunityService;
import com.network.service.PhotoService;
import com.network.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired private CommunitySubscriberRepository communitySubscriberRepository;

    @GetMapping("/communities")
    public String show(@RequestParam(required = false) String value,
                       @AuthenticationPrincipal User currentUser,
                       Model model) {
        List<CommunityDto> myCommunities = communityService.findCommunitiesByUserSubscribed(value, currentUser);
        List<CommunityDto> adminCommunities = communityService.getCommunitiesWhereUserIsAdmin(value, currentUser);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("myCommunities", myCommunities);
        model.addAttribute("value", value);
        model.addAttribute("adminCommunities", adminCommunities);
        return "communities-list";
    }

    @GetMapping("/communities/public/{id}")
    public String showPublic(@PathVariable int id,
                             @AuthenticationPrincipal User currentUser,
                             HttpServletRequest request,
                             Model model) {
        if(!communityRepository.existsById(id))
            return "redirect:" + request.getHeader("Referer");
        Community community = communityRepository.getById(id);
        model.addAttribute("community", community);
        model.addAttribute("posts", communityService.getPosts(community, currentUser));
        model.addAttribute("avatar", photoRepository.getCommunityAvatar(community));
        model.addAttribute("subscribers", communityService.getSubscribers(community));
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("subscription", communitySubscriberRepository.getByCommunityAndUser(community, currentUser));
        if(communityService.isRequestsListAllowed(currentUser, community))
            model.addAttribute("requests", communityService.getSubscriptionRequests(community));
        return "communit";
    }

    @GetMapping("/communities/public/{communityId}/subscribe")
    @ResponseStatus(HttpStatus.OK)
    public void subscribe(@AuthenticationPrincipal User currentUser,
                            @PathVariable int communityId) {
        communityService.updateSubscription(communityId, currentUser);
    }

    @GetMapping("/communities/public/{communityId}/subscription/{userId}/confirm")
    @ResponseStatus(HttpStatus.OK)
    public void confirmSubscription(@AuthenticationPrincipal User currentUser,
                                    @PathVariable int communityId,
                                    @PathVariable int userId) {
        communityService.confirmSubscription(communityId, userId, currentUser);
    }

    @PostMapping("/communities/create")
    public String createCommunity(@Valid @ModelAttribute Community community,
                       @RequestParam MultipartFile avatar,
                       @AuthenticationPrincipal User currentUser) throws IOException {
        communityService.createCommunity(community, currentUser);
        photoService.savePhoto(true, avatar, null, community, null);
        return "redirect:/communities";
    }

    @PostMapping("/communities/public/{id}")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody PostDto createPost(@PathVariable int id,
                              @AuthenticationPrincipal User currentUser,
                              @Valid @RequestParam @NotBlank String content) {
        return userService.savePost(id, content, currentUser, communityRepository.getById(id));
    }

    @PostMapping("/communities/public/{id}/edit")
    public String editCommunity(@PathVariable int id,
                              @Valid @ModelAttribute Community community,
                              @RequestParam MultipartFile avatar,
                              @AuthenticationPrincipal User currentUser) throws IOException {
        communityService.editCommunity(id, community, currentUser);
        photoService.savePhoto(true, avatar, null, community, null);
        return "redirect:/communities/public/" + id;
    }
}
