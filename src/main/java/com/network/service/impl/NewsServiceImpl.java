package com.network.service.impl;

import com.network.dto.NewsDto;
import com.network.model.Friendship;
import com.network.model.Photo;
import com.network.model.Post;
import com.network.model.User;
import com.network.repository.FriendshipRepository;
import com.network.service.FriendsService;
import com.network.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@Service
public class NewsServiceImpl implements NewsService {

    @Autowired private FriendsService friendsService;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public Set<NewsDto> getNews(User currentUser) {
        Set<NewsDto> news = new TreeSet<>();
        Set<User> friends = friendsService.getAllSubscriptions(currentUser);
        Set<User> subscriptions = friendsService.getAllFriends(currentUser);
        addPosts(news, currentUser, friends, subscriptions);
        addPhotos(news, friends, subscriptions);
        return news;
    }

    private void addPosts(Set<NewsDto> news, User currentUser, Set<User> friends, Set<User> subscriptions) {
        List<Post> posts = new ArrayList<>();
        currentUser.getCommunities().forEach(community -> posts.addAll(community.getPosts()));
        friends.forEach(friendship -> posts.addAll(friendship.getOwners()));
        subscriptions.forEach(friendship -> posts.addAll(friendship.getOwners()));
        posts.forEach(post -> {
            NewsDto newsDto = new NewsDto();
            newsDto.setPost(post);
            newsDto.setPublicationTime(simpleDateFormat.format(post.getPostTime()));
            news.add(newsDto);
        });
    }

    private void addPhotos(Set<NewsDto> news, Set<User> friends, Set<User> subscriptions) {
        List<Photo> photos = new ArrayList<>();
        friends.forEach(friendship -> photos.addAll(friendship.getPhotos()));
        subscriptions.forEach(friendship -> photos.addAll(friendship.getPhotos()));
        photos.forEach(photo -> {
            NewsDto newsDto = new NewsDto();
            newsDto.setPhoto(photo);
            newsDto.setPublicationTime(simpleDateFormat.format(photo.getDateOfPost()));
            news.add(newsDto);
        });
    }
}
