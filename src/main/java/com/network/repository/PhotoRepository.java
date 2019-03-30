package com.network.repository;

import com.network.model.Community;
import com.network.model.Photo;
import com.network.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PhotoRepository extends JpaRepository<Photo, Integer> {

    @Query(value = "SELECT * FROM Photo WHERE user_id = ?1 AND is_avatar = TRUE", nativeQuery = true)
    Photo getAvatarByUserId(int id);

    Photo getById(int id);

    Photo getAllByCommunityAndWasAvatarTrue(Community community);

    List<Photo> getByUserOrderByDateOfPostDesc(User user);

    List<Photo> getAllByUser_IdOrderByIdDesc(int id);

    List<Photo> getAllByPhotoAlbum_IdOrderByIdDesc(int id);

    @Query(value = "SELECT MAX(id) FROM photo WHERE id < ?1 AND user_id = ?2", nativeQuery = true)
    Integer getPreviousPhotoId(int id, int userId);

    @Query(value = "SELECT MIN(id) FROM photo WHERE id > ?1 AND user_id = ?2", nativeQuery = true)
    Integer getNextPhotoId(int id, int userId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE Photo SET is_avatar = FALSE WHERE user_id = ?1", nativeQuery = true)
    void updateAvatars(int id);

    @Modifying
    @Transactional
    @Query(value = "WITH cte AS (SELECT id FROM photo WHERE was_avatar = TRUE AND user_id = ?1 ORDER BY date_of_post DESC LIMIT 1) UPDATE photo p SET is_avatar = TRUE FROM cte WHERE p.id = cte.id", nativeQuery = true)
    void setPreviousAvatar(int id);
}
