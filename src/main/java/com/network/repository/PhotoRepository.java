package com.network.repository;

import com.network.model.Community;
import com.network.model.Photo;
import com.network.model.Post;
import com.network.model.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

public interface PhotoRepository extends CrudRepository<Photo, Integer> {

    @Query(value = "SELECT * FROM Photo WHERE user_id = ?1 ORDER BY date_of_post DESC", nativeQuery = true)
    List<Photo> getByUserId(int id);

    @Query(value = "SELECT id FROM Photo WHERE user_id = ?1 AND is_avatar = 1", nativeQuery = true)
    Integer getAvatarIdByUserId(int id);

    @Query(value = "SELECT * FROM Photo WHERE user_id = ?1 AND is_avatar = 1", nativeQuery = true)
    Photo getAvatarByUserId(int id);

    Photo getById(int id);

    Photo getAllByCommunityAndWasAvatarTrue(Community community);

    List<Photo> getByUserOrderByDateOfPostDesc(User user);

    List<Photo> getAllByUser_IdAndPhotoAlbumIsNull(int id);

    List<Photo> getAllByUser_Id(int id);

    List<Photo> getAllByPhotoAlbum_Id(int id);

    @Query(value = "SELECT MAX(id) FROM photo WHERE id < ?1 AND user_id = ?2", nativeQuery = true)
    Integer getPreviousPhotoId(int id, int userId);

    @Query(value = "SELECT MIN(id) FROM photo WHERE id > ?1 AND user_id = ?2", nativeQuery = true)
    Integer getNextPhotoId(int id, int userId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE Photo SET is_avatar = 0 WHERE user_id = ?1", nativeQuery = true)
    void updateAvatars(int id);
}
