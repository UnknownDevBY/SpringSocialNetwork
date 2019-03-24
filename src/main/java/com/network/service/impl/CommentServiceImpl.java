package com.network.service.impl;

import com.network.model.Comment;
import com.network.model.Hashtag;
import com.network.model.User;
import com.network.repository.CommentRepository;
import com.network.repository.HashtagRepository;
import com.network.repository.PhotoRepository;
import com.network.repository.PostRepository;
import com.network.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired private PostRepository postRepository;
    @Autowired private PhotoRepository photoRepository;
    @Autowired private CommentRepository commentRepository;
    @Autowired private HashtagRepository hashtagRepository;

    @Override
    public void addComment(String type, String content, int id, User currentUser) {
        Comment comment = new Comment();
        switch (type) {
            case "photo":
                comment.setPhoto(photoRepository.getById(id));
                break;
            case "post":
                comment.setPost(postRepository.getById(id));
                break;
            default:
                return;
        }
        List<Hashtag> hashtags = new ArrayList<>();
        comment.setHashtags(hashtags);
        getHashtags(content).forEach(action(hashtags));
        comment.setContent(content);
        comment.setUser(currentUser);
        commentRepository.save(comment);
    }

    public List<String> getHashtags(String content) {
        List<String> hashtags = new ArrayList<>();
        for(String word: content.split(" ")) {
            if(word.matches("#([^#]+)[\\s,;]*"))
                hashtags.add(word.substring(1));
        }
        return hashtags;
    }

    public Consumer<? super String> action(List<Hashtag> hashtags) {
        return tag -> {
            if(!hashtagRepository.existsByContent(tag)) {
                Hashtag hashtag = new Hashtag();
                hashtag.setContent(tag);
                hashtags.add(hashtag);
            }
        };
    }

}
