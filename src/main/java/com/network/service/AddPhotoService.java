package com.network.service;

import com.network.model.Community;
import com.network.model.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface AddPhotoService {

    void savePhoto(Boolean makeAvatar, MultipartFile newPhoto, User currentUser, Community community) throws IOException;
}
