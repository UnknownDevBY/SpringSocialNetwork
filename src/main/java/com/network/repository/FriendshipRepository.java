package com.network.repository;

import com.network.model.Friendship;
import com.network.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface FriendshipRepository extends CrudRepository<Friendship, Integer> {

    @Query(value = "SELECT is_confirmed FROM Friendship WHERE from_id = ?1 AND to_id = ?2", nativeQuery = true)
    Boolean getIsConfirmed(int id1, int id2);

    @Query(value = "SELECT * FROM Friendship WHERE (SELECT COUNT(id) FROM Friendship f WHERE to_id = ?1 AND is_confirmed = 1) = 1 AND from_id = ?1 AND is_confirmed = 1", nativeQuery = true)
    Set<Friendship> getAllFriends(int id);

    @Query(value = "SELECT * FROM Friendship WHERE (SELECT COUNT(id) FROM Friendship f WHERE to_id = ?1 AND is_confirmed = 1) = 0 AND from_id = ?1 AND is_confirmed = 1", nativeQuery = true)
    Set<Friendship> getAllSubscriptions(int id);

    @Query(value = "SELECT * FROM Friendship WHERE (SELECT COUNT(id) FROM Friendship f WHERE to_id = ?1 AND is_confirmed = 1) = 1 AND (SELECT COUNT(id) FROM Friendship f WHERE from_id = ?1 AND is_confirmed = 1) = 0 AND from_id != ?1", nativeQuery = true)
    Set<Friendship> getAllSubscribers(int id);

    Friendship getByFromAndTo(User from, User to);
}
