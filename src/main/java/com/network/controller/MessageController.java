package com.network.controller;

import com.network.model.User;
import com.network.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
public class MessageController {

    @Autowired private MessageService messageService;

    @Value("${s3.bucket}")
    private String bucketName;

    @GetMapping("/messages")
    public Map<String, Object> showMessages(@AuthenticationPrincipal User currentUser) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("currentUserId", currentUser.getId());
        map.put("messageWindows", messageService.getOpponents(currentUser.getId()));
        map.put("bucketName", bucketName);
        return map;
    }
}
