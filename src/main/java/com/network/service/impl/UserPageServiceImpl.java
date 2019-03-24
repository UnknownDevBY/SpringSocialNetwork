package com.network.service.impl;

import com.network.dto.PhotoDto;
import com.network.dto.PostDto;
import com.network.dto.PrivacySettingsDto;
import com.network.dto.UserDto;
import com.network.model.*;
import com.network.repository.*;
import com.network.service.UserPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Service
public class UserPageServiceImpl implements UserPageService {

    @Autowired private FriendshipRepository friendshipRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private PostRepository postRepository;
    @Autowired private PhotoRepository photoRepository;
    @Autowired private PrivacySettingsRepository privacySettingsRepository;
    @Autowired private LikesRepository likesRepository;
    @Autowired private CommentRepository commentRepository;
    @Autowired private CommentServiceImpl commentService;

    @Override
    public void savePost(int id, String content, User currentUser, Community community) {
        Post post = new Post();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        post.setPostTime(format.format(new java.util.Date()));
        post.setContent(content);
        if (currentUser != null) {
            post.setAuthor(currentUser);
            post.setOwner(userRepository.getById(id));
        } else post.setCommunity(community);
        List<Hashtag> hashtags = new ArrayList<>();
        post.setHashtags(hashtags);
        commentService.getHashtags(content).forEach(commentService.action(hashtags));
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
            friend.setAvatar(photoRepository.getAvatarByUserId(userId));
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
                    privacySettings != null ? privacySettings.getPostAuthors() : 'a',
                    privacySettings != null ? privacySettings.getComments() : 'a'
            );
        }
        else privacySet = new PrivacySettingsDto();
        return privacySet;
    }

    @Override
    public List<PostDto> getPosts(User pageUser, User currentUser) {
        List<PostDto> posts = new ArrayList<>();
        postRepository.getByOwnerOrderByPostTimeAsc(pageUser).forEach(setPostDto(posts, currentUser));
        return posts;
    }

    @Override
    public List<PhotoDto> getPhotos(User pageUser, User currentUser) {
        List<PhotoDto> photos = new ArrayList<>();
        photoRepository.getByUserOrderByDateOfPostDesc(pageUser).forEach(
                photo -> {
                    PhotoDto photoDto = new PhotoDto();
                    photoDto.setPhoto(photo);
                    photoDto.setComments(commentRepository.getAllByPhoto(photo));
                    photoDto.setLikesCount(likesRepository.countByPhoto(photo));
                    photoDto.setLikedByCurrentUser(likesRepository.getByPhotoAndUser(photo, currentUser) != null);
                    photos.add(photoDto);
                }
        );
        return photos;
    }

    public Consumer<? super Post> setPostDto(List<PostDto> posts, User currentUser) {
        return post -> {
            PostDto postDto = new PostDto();
            postDto.setPost(post);
            postDto.setComments(commentRepository.getAllByPost(post));
            postDto.setLikesCount(likesRepository.countByPost(post));
            postDto.setLikedByCurrentUser(likesRepository.getByPostAndUser(post, currentUser) != null);
            posts.add(postDto);
        };
    }
}
