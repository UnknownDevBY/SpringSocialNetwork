package com.network.service.impl;

import com.network.model.Likes;
import com.network.model.User;
import com.network.repository.LikesRepository;
import com.network.repository.PhotoRepository;
import com.network.repository.PostRepository;
import com.network.service.LikesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LikesServiceImpl implements LikesService {

    @Autowired private PostRepository postRepository;
    @Autowired private PhotoRepository photoRepository;
    @Autowired private LikesRepository likesRepository;

    @Override
    public void addLike(String type, int id, User currentUser) {
        Likes likes = new Likes();
        switch (type) {
            case "photo":
                if(likesRepository.countByUserIdAndPhotoId(currentUser.getId(), id) != 0) {
                    likesRepository.deleteByUserIdAndPhotoId(currentUser.getId(), id);
                    return;
                }
                likes.setPhoto(photoRepository.getById(id));
                break;
            case "post":
                if(likesRepository.countByUserIdAndPostId(currentUser.getId(), id) != 0) {
                    likesRepository.deleteByUserIdAndPostId(currentUser.getId(), id);
                    return;
                }
                likes.setPost(postRepository.getById(id));
                break;
            default:
                return;
        }
        likes.setUser(currentUser);
        likesRepository.save(likes);
    }
}
