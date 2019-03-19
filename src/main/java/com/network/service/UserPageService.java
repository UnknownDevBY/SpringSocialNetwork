package com.network.service;

import com.network.dto.PostDto;
import com.network.dto.PrivacySettingsDto;
import com.network.dto.UserDto;
import com.network.model.User;

import java.util.List;

public interface UserPageService {

    void savePost(int id, String content, User currentUser);
    List<UserDto> getFriends(User pageUser);
    boolean isFirstFriendToSecond(int id1, int id2);
    PrivacySettingsDto getPrivacySettings(User currentUser, User pageUser, boolean areFriends);
    List<PostDto> getPosts(User pageUser, User currentUser);
}
