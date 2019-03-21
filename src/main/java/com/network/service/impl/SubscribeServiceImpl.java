package com.network.service.impl;

import com.network.model.Community;
import com.network.model.User;
import com.network.repository.CommunityRepository;
import com.network.repository.UserRepository;
import com.network.service.SubscribeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubscribeServiceImpl implements SubscribeService {

    @Autowired private CommunityRepository communityRepository;
    @Autowired private UserRepository userRepository;

    @Override
    public void updateSubscription(int communityId, int userId) {
        Community community = communityRepository.getById(communityId);
        User user = userRepository.getById(userId);
        if(community != null && user != null) {
            if(community.getSubscribers().contains(user))
                community.getSubscribers().remove(user);
            else community.getSubscribers().add(user);
            communityRepository.save(community);
        }
    }
}
