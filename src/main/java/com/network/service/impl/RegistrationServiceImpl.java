package com.network.service.impl;

import com.network.model.Photo;
import com.network.model.User;
import com.network.repository.PhotoRepository;
import com.network.repository.UserRepository;
import com.network.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;

@Service
public class RegistrationServiceImpl implements RegistrationService {

    @Autowired private UserRepository userRepository;
    @Autowired private PhotoRepository photoRepository;
    @Autowired private PasswordEncoder passwordEncoder;

    @Override
    public void saveUser(User user, String pass, MultipartFile avatar) throws IOException {
        user.setPassword(passwordEncoder.encode(pass));
        userRepository.save(user);
        if(!avatar.isEmpty()) {
            Photo photo = new Photo();
            photo.setImg(avatar.getBytes());
            photo.setAvatar(true);
            photo.setWasAvatar(true);
            photo.setUser(user);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            photo.setDateOfPost(format.format(new java.util.Date()));
            photoRepository.save(photo);
        }
    }
}
