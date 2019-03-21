package com.network.service;

import com.network.dto.PhotoDto;
import com.network.model.User;

public interface PhotoService {

    PhotoDto getPhoto(int id, User currentUser);
}
