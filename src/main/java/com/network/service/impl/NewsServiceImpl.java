package com.network.service.impl;

import com.network.dto.NewsDto;
import com.network.model.Friendship;
import com.network.model.Photo;
import com.network.model.Post;
import com.network.model.User;
import com.network.repository.FriendshipRepository;
import com.network.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@Service
public class NewsServiceImpl implements NewsService {

    @Autowired private FriendshipRepository friendshipRepository;

    @Override
    public Set<NewsDto> getNews(User currentUser) {
        Set<NewsDto> news = new TreeSet<>();
        Set<Friendship> friends = friendshipRepository.getAllFriends(currentUser.getId());
        Set<Friendship> subscriptions = friendshipRepository.getAllSubscriptions(currentUser.getId());
        addPosts(news, currentUser, friends, subscriptions);
        addPhotos(news, friends, subscriptions);
        return news;
    }

    private void addPosts(Set<NewsDto> news, User currentUser, Set<Friendship> friends, Set<Friendship> subscriptions) {
        List<Post> posts = new ArrayList<>();
        currentUser.getCommunities().forEach(community -> posts.addAll(community.getPosts()));
        friends.forEach(friendship -> posts.addAll(friendship.getTo().getOwners()));
        subscriptions.forEach(friendship -> posts.addAll(friendship.getTo().getOwners()));
        posts.forEach(post -> {
            NewsDto newsDto = new NewsDto();
            newsDto.setPost(post);
            newsDto.setPublicationTime(post.getPostTime());
            news.add(newsDto);
        });
    }

    private void addPhotos(Set<NewsDto> news, Set<Friendship> friends, Set<Friendship> subscriptions) {
        List<Photo> photos = new ArrayList<>();
        friends.forEach(friendship -> photos.addAll(friendship.getTo().getPhotos()));
        subscriptions.forEach(friendship -> photos.addAll(friendship.getTo().getPhotos()));
        photos.forEach(photo -> {
            NewsDto newsDto = new NewsDto();
            newsDto.setPhoto(photo);
            newsDto.setPublicationTime(photo.getDateOfPost());
            news.add(newsDto);
        });
    }
}
