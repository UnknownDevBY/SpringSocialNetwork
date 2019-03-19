package com.network.service;

import com.network.model.User;

public interface LikesService {

    void addLike(String type, int id, User currentUser);
}
