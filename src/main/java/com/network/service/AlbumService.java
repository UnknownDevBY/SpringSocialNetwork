package com.network.service;

import com.network.model.PhotoAlbum;

import java.util.List;

public interface AlbumService {

    List<PhotoAlbum> getUserAlbums(int id);

    PhotoAlbum getDefaultAlbum(int id);
}
