package com.network.service;

import com.network.model.User;

public interface CommentService {

    void addComment(String type, String content, int id, User currentUser);
}
