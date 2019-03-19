package com.network.service.impl;

import com.network.model.User;
import com.network.repository.UserRepository;
import com.network.service.AddPhotoService;
import com.network.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class RegistrationServiceImpl implements RegistrationService {

    @Autowired private UserRepository userRepository;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private AddPhotoService addPhotoService;

    @Override
    public void saveUser(User user, String pass, MultipartFile avatar) throws IOException {
        user.setPassword(passwordEncoder.encode(pass));
        userRepository.save(user);
        addPhotoService.savePhoto(true, avatar, user);
    }
}
