package com.network.service;

import com.network.dto.UserDto;
import com.network.model.Friendship;
import com.network.model.User;

import java.util.List;
import java.util.Set;

public interface FriendsService {

    List<UserDto> setRelation(Set<User> relation);
    Set<User> getAllSubscribers(User pageUser);
    Set<User> getAllSubscriptions(User pageUser);
    Set<User> getAllFriends(User pageUser);
}
