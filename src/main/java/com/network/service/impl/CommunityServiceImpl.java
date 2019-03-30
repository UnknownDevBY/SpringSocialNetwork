package com.network.service.impl;

import com.network.component.PostDtoTransformer;
import com.network.component.UserDtoTransformer;
import com.network.dto.PostDto;
import com.network.dto.UserDto;
import com.network.model.Community;
import com.network.model.User;
import com.network.repository.CommunityRepository;
import com.network.repository.PostRepository;
import com.network.service.CommunityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommunityServiceImpl implements CommunityService {

    @Autowired private CommunityRepository communityRepository;
    @Autowired private PostRepository postRepository;
    @Autowired private PostDtoTransformer postDtoTransformer;
    @Autowired private UserDtoTransformer userDtoTransformer;

    @Override
    public List<Community> findCommunitiesByValue(String value, User currentUser) {
        return value == null ?
                communityRepository.getAllBySubscribersNotContains(currentUser) :
                communityRepository.getAllByTitleStartsWithAndSubscribersNotContains(value, currentUser);
    }

    @Override
    public List<Community> findCommunitiesByValueAndCurrentUser(String value, User currentUser) {
        return value == null ?
                communityRepository.getAllBySubscribersContains(currentUser) :
                communityRepository.getAllByTitleStartsWithAndSubscribersContains(value, currentUser);
    }

    @Override
    public List<PostDto> getPosts(Community community, User currentUser) {
        int currentUserId = currentUser.getId();
        return postRepository.getByCommunityOrderByPostTimeAsc(community).stream()
                .map(post -> postDtoTransformer.toPostDto(post, currentUserId))
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDto> getSubscribers(Community community) {
        return community.getSubscribers().stream()
                .map(user -> userDtoTransformer.toUserDto(user)).collect(Collectors.toList());
    }

    @Override
    public void createCommunity(Community community, User currentUser) {
        List<User> subscribers = new ArrayList<>();
        subscribers.add(currentUser);
        community.setAdmin(currentUser);
        community.setSubscribers(subscribers);
        communityRepository.save(community);
    }

    @Override
    public void updateSubscription(int communityId, User currentUser) {
        Community community = communityRepository.getById(communityId);
        if(community != null) {
            if(community.getSubscribers().contains(currentUser))
                community.getSubscribers().remove(currentUser);
            else community.getSubscribers().add(currentUser);
            communityRepository.save(community);
        }
    }
}
