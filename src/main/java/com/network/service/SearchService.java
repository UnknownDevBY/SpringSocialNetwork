package com.network.service;

import com.network.dto.UserDto;
import com.network.model.User;

import java.util.List;

public interface SearchService {

    List<User> findUsersByValue(String value);
    List<UserDto> reducedUsers(List<User> users, int currentUserId);
}
