package com.network.controller;

import com.network.model.User;
import com.network.repository.MessageRepository;
import com.network.service.ConversationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;

@Controller
public class ConversationController {

    @Autowired private MessageRepository messageRepository;
    @Autowired private ConversationService conversationService;

    @GetMapping("/conversations/{id}")
    public String openConversation(@PathVariable int id,
                                   @AuthenticationPrincipal User currentUser,
                                   Model model) {
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("opponentsId", id);
        model.addAttribute("messages", messageRepository.getAllMessages(currentUser.getId(), id));
        return "conversation";
    }

    @PostMapping("/conversations/{id}")
    public String sendMessage(@PathVariable int id,
                              @NotBlank @RequestParam String message,
                              @AuthenticationPrincipal User currentUser,
                              Model model) {
        conversationService.saveMessage(id, currentUser, message);
        return openConversation(id, currentUser, model);
    }
}