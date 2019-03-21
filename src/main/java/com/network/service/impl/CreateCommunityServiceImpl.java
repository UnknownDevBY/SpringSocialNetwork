package com.network.service.impl;

import com.network.model.Community;
import com.network.model.User;
import com.network.repository.CommunityRepository;
import com.network.service.CreateCommunityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CreateCommunityServiceImpl implements CreateCommunityService {

    @Autowired private CommunityRepository communityRepository;

    @Override
    public void createCommunity(Community community, User currentUser) {
        List<User> subscribers = new ArrayList<>();
        subscribers.add(currentUser);
        community.setAdmin(currentUser);
        community.setSubscribers(subscribers);
        communityRepository.save(community);
    }
}
