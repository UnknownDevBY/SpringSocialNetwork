package com.network.service;

import com.network.model.User;

public interface ConversationService {

    void saveMessage(int id, User currentUser, String content);
    boolean isValid(int currentUserId, int opponentsId);
}
