package com.network.service.impl;

import com.network.model.PhotoAlbum;
import com.network.repository.PhotoAlbumRepository;
import com.network.repository.PhotoRepository;
import com.network.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlbumServiceImpl implements AlbumService {

    @Autowired private PhotoAlbumRepository albumRepository;
    @Autowired private PhotoRepository photoRepository;

    @Override
    public List<PhotoAlbum> getUserAlbums(int id) {
        return albumRepository.getAllByUser_Id(id);
    }

    @Override
    public PhotoAlbum getDefaultAlbum(int id) {
        PhotoAlbum photoAlbum = new PhotoAlbum();
        photoAlbum.setTitle("All Photos");
        photoAlbum.setPhotos(photoRepository.getAllByUser_IdAndPhotoAlbumIsNull(id));
        return photoAlbum;
    }
}
