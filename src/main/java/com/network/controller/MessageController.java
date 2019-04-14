package com.network.controller;

import com.network.model.Message;
import com.network.model.User;
import com.network.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MessageController {

    @Autowired private MessageService messageService;

    @Value("${s3.bucket}")
    private String bucketName;

    @GetMapping("/messages")
    public String showMessages(@AuthenticationPrincipal User currentUser,
                               Model model) {
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("messageWindows", messageService.getOpponents(currentUser.getId()));
        model.addAttribute("bucketName", bucketName);
        return "messages";
    }
}
