package com.network.service;

import com.network.dto.UserDto;
import com.network.model.User;

public interface ConversationService {

    void saveMessage(int id, User currentUser, String content);
    UserDto getOpponent(int id);
}
