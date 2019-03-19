package com.network.service.impl;

import com.network.dto.UserDto;
import com.network.model.User;
import com.network.repository.PhotoRepository;
import com.network.repository.UserRepository;
import com.network.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SearchServiceImpl implements SearchService {

    @Autowired private PhotoRepository photoRepository;
    @Autowired private UserRepository userRepository;

    @Override
    public List<User> findUsersByValue(String value) {
        List<User> users;
        if(value == null) {
            users = userRepository.findAll();
        } else {
            users = userRepository.getAllByNameStartsWith(value);
            users.addAll(userRepository.getAllBySurnameStartsWith(value));
        }
        return users;
    }

    @Override
    public List<UserDto> reducedUsers(List<User> users, int currentUserId) {
        List<UserDto> reducedUsers = new ArrayList<>();
        users.forEach(user -> {
            int userId = user.getId();
            UserDto reducedUser = new UserDto();
            reducedUser.setUserName(user.getName());
            reducedUser.setAvatar(photoRepository.getAvatarByUserId(userId));
            reducedUser.setUserId(userId);
            reducedUser.setUserSurname(user.getSurname());
            if(userId != currentUserId)
                reducedUsers.add(reducedUser);
        });
        return reducedUsers;
    }
}
