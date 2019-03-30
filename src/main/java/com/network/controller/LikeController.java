package com.network.controller;

import com.network.model.User;
import com.network.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LikeController {

    @Autowired private LikeService likeService;

    @GetMapping("/likes/{type}/{id}")
    public String like(@PathVariable String type,
                       @PathVariable int id,
                       @AuthenticationPrincipal User currentUser,
                       HttpServletRequest request) {
        likeService.addLike(type, id, currentUser);
        return "redirect:" + request.getHeader("Referer");
    }
}
