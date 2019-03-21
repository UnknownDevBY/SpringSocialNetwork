package com.network.controller;

import com.network.service.SubscribeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

@Controller
public class SubscribeController {

    @Autowired private SubscribeService subscribeService;

    @GetMapping("/subscribe/{communityId}/{userId}")
    public String subscribe(@PathVariable int communityId, @PathVariable int userId, HttpServletRequest request) {
        subscribeService.updateSubscription(communityId, userId);
        return "redirect:" + request.getHeader("Referer");
    }
}
