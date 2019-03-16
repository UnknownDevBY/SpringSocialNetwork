package com.network.service;

import com.network.dto.UserDto;
import com.network.model.Friendship;

import java.util.List;
import java.util.Set;

public interface FriendsService {

    List<UserDto> setRelation(Set<Friendship> relation, String attributeTitle);
}
