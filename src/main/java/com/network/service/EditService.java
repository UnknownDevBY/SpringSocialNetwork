package com.network.service;

import com.network.model.User;

public interface EditService {

    String saveEdit(User currentUser, User user, String pass);
}
