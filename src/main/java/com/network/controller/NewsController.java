package com.network.controller;

import com.network.aspect.annotation.Authorization;
import com.network.model.User;
import com.network.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
public class NewsController {

    @Autowired private NewsService newsService;

    @Value("${s3.bucket}")
    private String bucketName;

    @GetMapping("/news")
    public Map<String, Object> showNews(@AuthenticationPrincipal User currentUser) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("currentUserId", currentUser.getId());
        map.put("allNews", newsService.getNews(currentUser));
        map.put("bucketName", bucketName);
        return map;
    }

    @Authorization
    @PostMapping("/news")
    public Map<String, Object> showNewsPost(@AuthenticationPrincipal User currentUser) {
        return showNews(currentUser);
    }
}
