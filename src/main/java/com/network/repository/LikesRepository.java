package com.network.repository;

import com.network.model.Likes;
import com.network.model.Photo;
import com.network.model.Post;
import com.network.model.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface LikesRepository extends CrudRepository<Likes, Integer> {

    int countByPost(Post post);

    int countByPhoto(Photo photo);

    Likes getByPostAndUser(Post post, User user);

    @Query(value = "SELECT COUNT(*) FROM likes WHERE user_id = ?1 AND post_id = ?2", nativeQuery = true)
    int countByUserIdAndPostId(int userId, int postId);

    @Query(value = "SELECT COUNT(*) FROM likes WHERE user_id = ?1 AND photo_id = ?2", nativeQuery = true)
    int countByUserIdAndPhotoId(int userId, int photoId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM likes WHERE user_id = ?1 AND post_id = ?2", nativeQuery = true)
    void deleteByUserIdAndPostId(int userId, int postId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM likes WHERE user_id = ?1 AND photo_id = ?2", nativeQuery = true)
    void deleteByUserIdAndPhotoId(int userId, int photoId);
}
