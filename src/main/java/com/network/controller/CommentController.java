package com.network.controller;

import com.network.model.User;
import com.network.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class CommentController {

    @Autowired private CommentService commentService;

    @PostMapping("/comment/{type}/{id}")
    public String like(@PathVariable String type,
                       @PathVariable int id,
                       @RequestParam String content,
                       @AuthenticationPrincipal User currentUser,
                       HttpServletRequest request) {
        commentService.addComment(type, content, id, currentUser);
        return "redirect:" + request.getHeader("Referer");
    }
}
