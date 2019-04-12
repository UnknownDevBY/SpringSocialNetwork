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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
public class CommunityController {

    @Autowired private CommunityService communityService;
    @Autowired private CommunityRepository communityRepository;
    @Autowired private PhotoRepository photoRepository;
    @Autowired private PhotoService photoService;
    @Autowired private UserService userService;

    @Value("${s3.bucket}")
    private String bucketName;

    @GetMapping("/communities")
    public Map<String, Object> show(@RequestParam(required = false) String value,
                                    @AuthenticationPrincipal User currentUser) {
        List<Community> myCommunities = communityService.findCommunitiesByValueAndCurrentUser(value, currentUser);
        List<Community> allCommunities = communityService.findCommunitiesByValue(value, currentUser);
        allCommunities.removeAll(myCommunities);
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("currentUserId", currentUser.getId());
        map.put("myCommunities", myCommunities);
        map.put("allCommunities", allCommunities);
        return map;
    }

    @GetMapping("/communities/public/{id}")
    public Map<String, Object> showPublic(@PathVariable int id,
                             @AuthenticationPrincipal User currentUser) {
        if(!communityRepository.existsById(id))
            return null;
        Community community = communityRepository.getById(id);
        List<UserDto> subscribers = communityService.getSubscribers(community);
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("community", community);
        map.put("posts", communityService.getPosts(community, currentUser));
        map.put("avatar", photoRepository.getAllByCommunityAndWasAvatarTrue(community));
        map.put("subscribers", subscribers.size() <= 6 ? subscribers : subscribers.subList(0, 6));
        map.put("currentUserId", currentUser != null ? currentUser.getId() : 0);
        map.put("isSubscribed", community.getSubscribers().contains(currentUser));
        map.put("bucketName", bucketName);
        map.put("admin", community.getAdmin());
        return map;
    }

    @GetMapping("/communities/create")
    public String openCreateCommunity() {
        return "createGroup";
    }

    @GetMapping("/communities/public/{communityId}/subscribe")
    @ResponseStatus(HttpStatus.OK)
    public void subscribe(@AuthenticationPrincipal User currentUser,
                            @PathVariable int communityId) {
        communityService.updateSubscription(communityId, currentUser);
    }

    @PostMapping("/communities/create")
    public void createCommunity(@ModelAttribute Community community,
                       @RequestParam MultipartFile avatar,
                       @AuthenticationPrincipal User currentUser) throws IOException {
        photoService.savePhoto(true, avatar, null, community, null);
        communityService.createCommunity(community, currentUser);
    }

    @PostMapping("/communities/public/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void createPost(@PathVariable int id,
                           @AuthenticationPrincipal User currentUser,
                           @RequestParam String content) {
        userService.savePost(id, content, currentUser, communityRepository.getById(id));
    }
}
