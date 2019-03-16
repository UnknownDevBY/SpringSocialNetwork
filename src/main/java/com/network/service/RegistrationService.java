package com.network.service;

import com.network.model.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface RegistrationService {

    void saveUser(User user, String pass, MultipartFile avatar) throws IOException;
}
