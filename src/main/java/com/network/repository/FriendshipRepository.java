package com.network.repository;

import com.network.model.Friendship;
import com.network.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface FriendshipRepository extends CrudRepository<Friendship, Integer> {

    Set<Friendship> getAllByFrom(User from);

    Set<Friendship> getAllByTo(User to);

    Friendship getByFrom_IdAndTo_Id(int id1, int id2);

    Friendship getByFromAndTo(User from, User to);
}
