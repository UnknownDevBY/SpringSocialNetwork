package com.network.controller;

import com.network.model.User;
import com.network.repository.UserRepository;
import com.network.service.ModifyUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ModifyUserController {

    @Autowired private UserRepository userRepository;
    @Autowired private ModifyUserService modifyUserService;

    @GetMapping("/users/friendship/{id}")
    public String addFriend(@PathVariable int id,
                            HttpServletRequest request,
                            @AuthenticationPrincipal User currentUser) {
        User pageUser = userRepository.getById(id);
        modifyUserService.modifyRelation(currentUser, pageUser);
        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }
}
