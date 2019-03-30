package com.network.service.impl;

import com.network.component.PostDtoTransformer;
import com.network.component.UserDtoTransformer;
import com.network.dto.PostDto;
import com.network.dto.UserDto;
import com.network.model.Comment;
import com.network.model.Post;
import com.network.model.User;
import com.network.repository.CommentRepository;
import com.network.repository.PostRepository;
import com.network.repository.UserRepository;
import com.network.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SearchServiceImpl implements SearchService {

    @Autowired private UserRepository userRepository;
    @Autowired private PostRepository postRepository;
    @Autowired private CommentRepository commentRepository;
    @Autowired private PostDtoTransformer postDtoTransformer;
    @Autowired private UserDtoTransformer userDtoTransformer;

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
        users.removeIf(user -> user.getId() == currentUserId);
        return users.stream().map(user -> userDtoTransformer.toUserDto(user)).collect(Collectors.toList());
    }

    @Override
    public List<PostDto> findAllPostsByHashtag(String hashtag, User currentUser) {
        int currentUserId = currentUser.getId();
        List<Post> posts = postRepository.getByContentContains("#" + hashtag);
        return posts != null
                ? posts.stream().map(post -> postDtoTransformer.toPostDto(post, currentUserId)).collect(Collectors.toList())
                : null;
    }

    @Override
    public List<Comment> findAllCommentsByHashtag(String hashtag) {
        return commentRepository.getByContentContains("#" + hashtag);
    }
}
