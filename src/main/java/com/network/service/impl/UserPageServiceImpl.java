package com.network.service.impl;

import com.network.dto.PrivacySettingsDto;
import com.network.dto.UserDto;
import com.network.model.Post;
import com.network.model.PrivacySettings;
import com.network.model.User;
import com.network.repository.*;
import com.network.service.UserPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserPageServiceImpl implements UserPageService {

    @Autowired private FriendshipRepository friendshipRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private PostRepository postRepository;
    @Autowired private PhotoRepository photoRepository;
    @Autowired private PrivacySettingsRepository privacySettingsRepository;

    @Override
    public void savePost(int id, String content, User currentUser) {
        Post post = new Post();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        post.setPostTime(format.format(new java.util.Date()));
        post.setContent(content);
        post.setAuthor(currentUser);
        post.setOwner(userRepository.getById(id));
        postRepository.save(post);
    }

    @Override
    public List<UserDto> getFriends(User pageUser) {
        List<UserDto> friends = new ArrayList<>();
        friendshipRepository.getAllFriends(pageUser.getId()).forEach(e -> {
            User user = e.getTo();
            int userId = user.getId();
            UserDto friend = new UserDto();
            friend.setUserName(user.getName());
            friend.setAvatarId(photoRepository.getAvatarIdByUserId(userId));
            friend.setUserId(userId);
            friend.setUserSurname(user.getSurname());
            friends.add(friend);
        });
        return friends;
    }

    @Override
    public boolean isFirstFriendToSecond(int id1, int id2) {
        Boolean value = friendshipRepository.getIsConfirmed(id1, id2);
        return value != null ? value : false;
    }

    @Override
    public PrivacySettingsDto getPrivacySettings(User currentUser, User pageUser, boolean areFriends) {
        PrivacySettings privacySettings = privacySettingsRepository.getByUser(pageUser);
        PrivacySettingsDto privacySet;
        if(currentUser == null || currentUser.getId() != pageUser.getId()) {
            privacySet = new PrivacySettingsDto(
                    areFriends,
                    privacySettings != null ? privacySettings.getMessages() : 'a',
                    privacySettings != null ? privacySettings.getFullInfo() : 'a',
                    privacySettings != null ? privacySettings.getPhotos() : 'a',
                    privacySettings != null ? privacySettings.getFriends() : 'a',
                    privacySettings != null ? privacySettings.getPostAuthors() : 'a'
            );
        }
        else privacySet = new PrivacySettingsDto();
        return privacySet;
    }
}
