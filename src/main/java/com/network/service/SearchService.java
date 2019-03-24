package com.network.service;

import com.network.dto.PostDto;
import com.network.dto.UserDto;
import com.network.model.Comment;
import com.network.model.User;

import java.util.List;

public interface SearchService {

    List<User> findUsersByValue(String value);
    List<UserDto> reducedUsers(List<User> users, int currentUserId);
    List<PostDto> findAllPostsByHashtag(String hashtag, User currentUser);
    List<Comment> findAllCommentsByHashtag(String hashtag);
}
