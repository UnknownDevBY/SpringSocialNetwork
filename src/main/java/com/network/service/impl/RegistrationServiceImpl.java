package com.network.service.impl;

import com.network.model.User;
import com.network.repository.UserRepository;
import com.network.service.PhotoService;
import com.network.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Date;

@Service
public class RegistrationServiceImpl implements RegistrationService {

    @Autowired private UserRepository userRepository;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private PhotoService photoService;

    @Override
    public void saveUser(User user, String pass, MultipartFile avatar) throws IOException {
        user.setPassword(passwordEncoder.encode(pass));
        userRepository.save(user);
        photoService.savePhoto(true, avatar, user, null, null);
    }

    @Override
    public String getError(User user) {
        if(System.currentTimeMillis() - Date.valueOf(user.getDateOfBirth()).getTime() < 441_504_000_000L) {
            return "Вы должны быть старше 14-и лет";
        }
        if(userRepository.getByEmail(user.getEmail()) != null) {
            return "Пользователь с таким логином уже существует";
        }
        return null;
    }
}
