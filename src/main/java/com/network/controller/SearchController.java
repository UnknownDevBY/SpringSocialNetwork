package com.network.controller;

import com.network.model.User;
import com.network.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
public class SearchController {

    @Autowired private SearchService searchService;

    @Value("${s3.bucket}")
    private String bucketName;

    @RequestMapping("/search")
    public String showAllUsers(@RequestParam(required = false) String value,
                               @RequestParam(required = false) String hashtag,
                               @AuthenticationPrincipal User currentUser) {
        Map<String, Object> map = new LinkedHashMap<>();
        if(hashtag != null) {
            map.put("posts", searchService.findAllPostsByHashtag(hashtag, currentUser));
            map.put("comments", searchService.findAllCommentsByHashtag(hashtag));
        } else {
            List<User> users = searchService.findUsersByValue(value);
            map.put("users", searchService.reducedUsers(users, currentUser != null ? currentUser.getId() : 0));
        }
        map.put("currentUserId", currentUser.getId());
        map.put("bucketName", bucketName);
        return "search";
    }

}
