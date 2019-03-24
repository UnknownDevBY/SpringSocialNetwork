package com.network.service;

import com.network.model.User;

public interface AddAlbumService {
    void addAlbum(String title, User currentUser);
}
