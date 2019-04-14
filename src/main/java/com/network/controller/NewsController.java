package com.network.controller;

import com.network.aspect.annotation.Authorization;
import com.network.model.User;
import com.network.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class NewsController {

    @Autowired private NewsService newsService;

    @Value("${s3.bucket}")
    private String bucketName;

    @GetMapping("/news")
    public String showNews(@AuthenticationPrincipal User currentUser,
                           Model model) {
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("allNews", newsService.getNews(currentUser));
        model.addAttribute("bucketName", bucketName);
        return "news";
    }

    @Authorization
    @PostMapping("/news")
    public String showNewsPost(@AuthenticationPrincipal User currentUser,
                           Model model) {
        return showNews(currentUser, model);
    }
}
