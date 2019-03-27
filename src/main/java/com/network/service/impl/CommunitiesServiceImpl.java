package com.network.service.impl;

import com.network.dto.PostDto;
import com.network.dto.UserDto;
import com.network.model.Community;
import com.network.model.User;
import com.network.repository.CommunityRepository;
import com.network.repository.PhotoRepository;
import com.network.repository.PostRepository;
import com.network.service.CommunitiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommunitiesServiceImpl implements CommunitiesService {

    @Autowired private CommunityRepository communityRepository;
    @Autowired private PostRepository postRepository;
    @Autowired private UserPageServiceImpl userPageService;
    @Autowired private FriendsServiceImpl friendsService;

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
        List<PostDto> posts = new ArrayList<>();
        postRepository.getByCommunityOrderByPostTimeAsc(community).forEach(userPageService.setPostDto(posts, currentUser));
        return posts;
    }

    @Override
    public List<UserDto> getSubscribers(Community community) {
        List<UserDto> friends = new ArrayList<>();
        community.getSubscribers().forEach(friendsService.setUserDto(friends));
        return friends;
    }
}
