package com.network.service.impl;

import com.network.dto.PostDto;
import com.network.dto.UserDto;
import com.network.model.Community;
import com.network.model.User;
import com.network.repository.*;
import com.network.service.CommunitiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommunitiesServiceImpl implements CommunitiesService {

    @Autowired private CommunityRepository communityRepository;
    @Autowired private PostRepository postRepository;
    @Autowired private CommentRepository commentRepository;
    @Autowired private LikesRepository likesRepository;
    @Autowired private PhotoRepository photoRepository;

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
        postRepository.getByCommunityOrderByPostTimeAsc(community).forEach(
                post -> {
                    PostDto postDto = new PostDto();
                    postDto.setPost(post);
                    postDto.setComments(commentRepository.getAllByPost(post));
                    postDto.setLikesCount(likesRepository.countByPost(post));
                    postDto.setLikedByCurrentUser(likesRepository.getByPostAndUser(post, currentUser) != null);
                    posts.add(postDto);
                }
        );
        return posts;
    }

    @Override
    public List<UserDto> getSubscribers(Community community) {
        List<UserDto> friends = new ArrayList<>();
        community.getSubscribers().forEach(user -> {
            int userId = user.getId();
            UserDto friend = new UserDto();
            friend.setUserName(user.getName());
            friend.setAvatar(photoRepository.getAvatarByUserId(userId));
            friend.setUserId(userId);
            friend.setUserSurname(user.getSurname());
            friends.add(friend);
        });
        return friends;
    }
}
