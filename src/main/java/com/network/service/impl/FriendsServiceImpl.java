package com.network.service.impl;

import com.network.dto.UserDto;
import com.network.model.Friendship;
import com.network.model.User;
import com.network.repository.FriendshipRepository;
import com.network.repository.PhotoRepository;
import com.network.service.FriendsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

@Service
public class FriendsServiceImpl implements FriendsService {

    @Autowired private PhotoRepository photoRepository;
    @Autowired private FriendshipRepository friendshipRepository;

    @Override
    public List<UserDto> setRelation(Set<User> relation) {
        List<UserDto> relations = new ArrayList<>();
        relation.forEach(setUserDto(relations));
        return relations;
    }

    @Override
    public Set<User> getAllSubscribers(User pageUser) {
        Set<User> subscribers = new LinkedHashSet<>();
        friendshipRepository.getAllByTo(pageUser).forEach(friendship -> subscribers.add(friendship.getFrom()));
        friendshipRepository.getAllByFrom(pageUser).forEach(friendship -> subscribers.remove(friendship.getTo()));
        return subscribers;
    }

    @Override
    public Set<User> getAllSubscriptions(User pageUser) {
        Set<User> subscriptions = new LinkedHashSet<>();
        friendshipRepository.getAllByFrom(pageUser).forEach(friendship -> subscriptions.add(friendship.getTo()));
        friendshipRepository.getAllByTo(pageUser).forEach(friendship -> subscriptions.remove(friendship.getFrom()));
        return subscriptions;
    }

    @Override
    public Set<User> getAllFriends(User pageUser) {
        Set<User> friends = new LinkedHashSet<>();
        List<User> from = new ArrayList<>();
        friendshipRepository.getAllByTo(pageUser).forEach(friendship -> from.add(friendship.getFrom()));
        List<User> to = new ArrayList<>();
        friendshipRepository.getAllByFrom(pageUser).forEach(friendship -> to.add(friendship.getTo()));
        to.forEach(subscriber -> {
            if(from.contains(subscriber))
                friends.add(subscriber);
        });
        return friends;
    }

    public Consumer<? super User> setUserDto(List<UserDto> relations) {
        return user -> {
            int userId = user.getId();
            UserDto friend = new UserDto();
            friend.setUserName(user.getName());
            friend.setAvatar(photoRepository.getAvatarByUserId(userId));
            friend.setUserId(userId);
            friend.setUserSurname(user.getSurname());
            relations.add(friend);
        };
    }


}
