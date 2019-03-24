package com.network.repository;

import com.network.model.PhotoAlbum;
import com.network.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PhotoAlbumRepository extends CrudRepository<PhotoAlbum, Integer> {

    List<PhotoAlbum> getByUser(User currentUser);

    boolean existsByUser(User currentUser);

    boolean existsByTitleAndUser(String title, User user);

    PhotoAlbum getByUserAndTitle(User user, String title);

    @Query(value = "SELECT title FROM photo_album WHERE user_id = ?1", nativeQuery = true)
    List<String> getAllTitlesByUserId(int id);

    List<PhotoAlbum> getAllByUser_Id(int id);
}
