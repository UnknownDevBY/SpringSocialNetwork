package com.network.controller;

import com.network.model.User;
import com.network.service.DeleteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

@Controller
public class DeleteController {

    @Autowired private DeleteService deleteService;

    @GetMapping("/delete/{type}/{id}")
    public String delete(@PathVariable String type,
                         @PathVariable int id,
                         @AuthenticationPrincipal User currentUser,
                         HttpServletRequest request) {
        deleteService.deleteObject(type, id, currentUser);
        return "redirect:" + request.getHeader("Referer");
    }
}
