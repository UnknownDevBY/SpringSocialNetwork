package com.network.service.impl;

import com.google.common.io.Files;
import com.network.model.Photo;
import com.network.model.User;
import com.network.repository.PhotoRepository;
import com.network.service.AddPhotoService;
import com.network.service.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Random;

@Service
public class AddPhotoServiceImpl implements AddPhotoService {

    @Autowired private PhotoRepository photoRepository;
    @Autowired private S3Service s3Service;

    @Override
    public void savePhoto(Boolean makeAvatar, MultipartFile newPhoto, User currentUser) throws IOException {
        if(!newPhoto.isEmpty()) {
            boolean isAvatar = makeAvatar != null;
            Photo photo = new Photo();
            String title = generateRandomString() + "." + Files.getFileExtension(newPhoto.getOriginalFilename());
            photo.setTitle(title);
            photo.setAvatar(isAvatar);
            photo.setWasAvatar(isAvatar);
            photo.setUser(currentUser);
            if(isAvatar)
                photoRepository.updateAvatars(currentUser.getId());
            s3Service.uploadFile(title, newPhoto);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            photo.setDateOfPost(format.format(new java.util.Date()));
            photoRepository.save(photo);
        }
    }

    private String generateRandomString() {
        byte[] array = new byte[15];
        new Random().nextBytes(array);
        return new String(array, Charset.forName("UTF-8"));
    }
}
