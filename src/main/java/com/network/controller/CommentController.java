package com.network.controller;

import com.network.model.User;
import com.network.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class CommentController {

    @Autowired private CommentService commentService;

    @PostMapping("/comment/{type}/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void like(@PathVariable String type,
                       @PathVariable int id,
                       @RequestParam String content,
                       @AuthenticationPrincipal User currentUser) {
        commentService.addComment(type, content, id, currentUser);
    }
}
