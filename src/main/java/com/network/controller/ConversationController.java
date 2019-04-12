package com.network.controller;

import com.network.model.User;
import com.network.repository.MessageRepository;
import com.network.service.ConversationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
public class ConversationController {

    @Autowired private MessageRepository messageRepository;
    @Autowired private ConversationService conversationService;

    @GetMapping("/conversations/{id}")
    public Map<String, Object> openConversation(@PathVariable int id,
                                             @AuthenticationPrincipal User currentUser) {
        if(conversationService.isValid(currentUser.getId(), id))
            return null;
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("currentUserId", currentUser.getId());
        map.put("opponentsId", id);
        map.put("messages", messageRepository.getAllMessages(currentUser.getId(), id));
        return map;
    }

    @PostMapping("/conversations/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void sendMessage(@PathVariable int id,
                              @NotBlank @RequestParam String content,
                              @AuthenticationPrincipal User currentUser) {
        conversationService.saveMessage(id, currentUser, content);
    }
}
