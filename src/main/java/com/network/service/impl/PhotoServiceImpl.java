package com.network.service.impl;

import com.network.dto.PhotoDto;
import com.network.model.Photo;
import com.network.model.User;
import com.network.repository.CommentRepository;
import com.network.repository.LikesRepository;
import com.network.repository.PhotoRepository;
import com.network.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PhotoServiceImpl implements PhotoService {

    @Autowired private PhotoRepository photoRepository;
    @Autowired private LikesRepository likesRepository;
    @Autowired private CommentRepository commentRepository;

    @Override
    public PhotoDto getPhoto(int id, User currentUser) {
        PhotoDto photoDto = new PhotoDto();
        Photo photo = photoRepository.getById(id);
        photoDto.setPhoto(photo);
        photoDto.setComments(commentRepository.getAllByPhoto(photo));
        photoDto.setLikedByCurrentUser(likesRepository.getByPhotoAndUser(photo, currentUser) != null);
        photoDto.setLikesCount(likesRepository.countByPhoto(photo));
        return photoDto;
    }
}
