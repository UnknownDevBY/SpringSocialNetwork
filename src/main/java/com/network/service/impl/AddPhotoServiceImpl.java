package com.network.service.impl;

import com.network.model.Photo;
import com.network.model.User;
import com.network.repository.PhotoRepository;
import com.network.service.AddPhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;

@Service
public class AddPhotoServiceImpl implements AddPhotoService {

    @Autowired private PhotoRepository photoRepository;

    @Override
    public void savePhoto(Boolean makeAvatar, MultipartFile newPhoto, User currentUser) throws IOException {
        if(!newPhoto.isEmpty()) {
            boolean isAvatar = makeAvatar != null;
            Photo photo = new Photo();
            photo.setImg(newPhoto.getBytes());
            photo.setAvatar(isAvatar);
            photo.setWasAvatar(isAvatar);
            photo.setUser(currentUser);
            if(isAvatar)
                photoRepository.updateAvatars(currentUser.getId());
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            photo.setDateOfPost(format.format(new java.util.Date()));
            photoRepository.save(photo);
        }
    }
}
