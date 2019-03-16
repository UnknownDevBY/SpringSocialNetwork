package com.network.controller;

import com.network.model.User;
import com.network.repository.UserRepository;
import com.network.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class SearchController {

    @Autowired private UserRepository userRepository;
    @Autowired private SearchService searchService;

    @RequestMapping("/search")
    public String showAllUsers(@RequestParam(required = false) String value,
                               @AuthenticationPrincipal User currentUser,
                               Model model) {
        List<User> users;
        if(value == null) {
            users = userRepository.findAll();
        } else {
            users = userRepository.getAllByNameStartsWith(value);
            users.addAll(userRepository.getAllBySurnameStartsWith(value));
        }
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("users", searchService.reducedUsers(users, currentUser != null ? currentUser.getId() : 0));
        return "search";
    }
}
