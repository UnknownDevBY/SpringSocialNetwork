package com.network.controller;

import com.network.model.User;
import com.network.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SearchController {

    @Autowired private SearchService searchService;

    @RequestMapping("/search")
    public String showAllUsers(@RequestParam(required = false) String value,
                               @AuthenticationPrincipal User currentUser,
                               Model model) {
        model.addAttribute("posts", searchService.findAllPostsWithHashtags(
                currentUser != null ? currentUser.getId() : 0));
        model.addAttribute("communities", searchService.getAllCommunities());
        model.addAttribute("users", searchService.getAllUsers(currentUser));
        model.addAttribute("value", value);
        model.addAttribute("currentUser", currentUser);
        return "search";
    }

}
