package com.network.service.impl;

import com.network.model.Message;
import com.network.model.User;
import com.network.repository.MessageRepository;
import com.network.repository.UserRepository;
import com.network.service.ConversationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class ConversationServiceImpl implements ConversationService {

    @Autowired private UserRepository userRepository;
    @Autowired private MessageRepository messageRepository;

    @Override
    public void saveMessage(int id, User currentUser, String content) {
        Message message = new Message();
        message.setFrom(currentUser);
        message.setTo(userRepository.getById(id));
        message.setContent(content);
        message.setSendingTime(new Timestamp(System.currentTimeMillis()));
        messageRepository.save(message);
    }
}
