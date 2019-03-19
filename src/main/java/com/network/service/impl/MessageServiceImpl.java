package com.network.service.impl;

import com.network.dto.MessageDto;
import com.network.dto.UserDto;
import com.network.model.Message;
import com.network.repository.MessageRepository;
import com.network.repository.PhotoRepository;
import com.network.repository.UserRepository;
import com.network.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired private UserRepository userRepository;
    @Autowired private MessageRepository messageRepository;
    @Autowired private PhotoRepository photoRepository;

    @Override
    public List<MessageDto> getOpponents(int id) {
        List<MessageDto> messageWindows = new ArrayList<>();
        messageRepository.getOpponentsId(id).forEach(integer -> {
            UserDto friend = new UserDto();
            friend.setUserName(userRepository.getNameById(integer));
            friend.setUserSurname(userRepository.getSurnameById(integer));
            friend.setUserId(integer);
            friend.setAvatar(photoRepository.getAvatarByUserId(integer));
            MessageDto messageWindow = new MessageDto();
            Message lastMessage = messageRepository.getLastMessage(id, integer);
            messageWindow.setAuthorId(lastMessage.getFrom().getId());
            messageWindow.setLastMessage(lastMessage.getContent());
            messageWindow.setFriend(friend);
            messageWindows.add(messageWindow);
        });
        return messageWindows;
    }
}
