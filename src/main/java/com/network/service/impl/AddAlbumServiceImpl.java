package com.network.service.impl;

import com.network.model.PhotoAlbum;
import com.network.model.User;
import com.network.repository.PhotoAlbumRepository;
import com.network.service.AddAlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddAlbumServiceImpl implements AddAlbumService {

    @Autowired private PhotoAlbumRepository albumRepository;

    @Override
    public void addAlbum(String title, User currentUser) {
        if(!albumRepository.existsByTitleAndUser(title, currentUser)) {
            PhotoAlbum album = new PhotoAlbum();
            album.setTitle(title);
            album.setUser(currentUser);
            albumRepository.save(album);
        }
    }
}
