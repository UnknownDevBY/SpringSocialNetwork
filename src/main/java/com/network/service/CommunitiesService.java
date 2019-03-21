package com.network.service;

import com.network.dto.PostDto;
import com.network.dto.UserDto;
import com.network.model.Community;
import com.network.model.User;

import java.util.List;

public interface CommunitiesService {

    List<Community> findCommunitiesByValue(String value, User currentUser);
    List<Community> findCommunitiesByValueAndCurrentUser(String value, User currentUser);
    List<PostDto> getPosts(Community community, User currentUser);
    List<UserDto> getSubscribers(Community community);
}
