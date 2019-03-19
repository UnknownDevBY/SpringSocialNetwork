package com.network.service.impl;

import com.network.dto.UserDto;
import com.network.model.Friendship;
import com.network.model.User;
import com.network.repository.PhotoRepository;
import com.network.service.FriendsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class FriendsServiceImpl implements FriendsService {

    @Autowired private PhotoRepository photoRepository;

    @Override
    public List<UserDto> setRelation(Set<Friendship> relation, String attributeTitle) {
        List<UserDto> relations = new ArrayList<>();
        relation.forEach(e -> {
            User user = attributeTitle.equals("subscribers") ? e.getFrom() : e.getTo();
            int userId = user.getId();
            UserDto friend = new UserDto();
            friend.setUserName(user.getName());
            friend.setAvatar(photoRepository.getAvatarByUserId(userId));
            friend.setUserId(userId);
            friend.setUserSurname(user.getSurname());
            relations.add(friend);
        });
        return relations;
    }
}
