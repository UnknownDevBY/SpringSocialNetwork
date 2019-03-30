package com.network.service.impl;

import com.network.component.MessageDtoTransformer;
import com.network.dto.MessageDto;
import com.network.repository.MessageRepository;
import com.network.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired private MessageDtoTransformer messageDtoTransformer;
    @Autowired private MessageRepository messageRepository;

    @Override
    public List<MessageDto> getOpponents(int currentUserId) {
        return messageRepository.getOpponentsId(currentUserId).stream()
                .map(opponentId -> messageDtoTransformer.toMessageDto(currentUserId, opponentId))
                .collect(Collectors.toList());
    }
}
