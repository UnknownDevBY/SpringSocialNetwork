package com.network.controller;

import com.network.dto.UserDto;
import com.network.model.Message;
import com.network.model.User;
import com.network.dto.MessageDto;
import com.network.repository.MessageRepository;
import com.network.repository.PhotoRepository;
import com.network.repository.UserRepository;
import com.network.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.ArrayList;
import java.util.List;

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
