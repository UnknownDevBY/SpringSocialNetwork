package com.network.repository;

import com.network.model.Community;
import com.network.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CommunityRepository extends CrudRepository<Community, Integer> {

    Community getById(int id);

    List<Community> findAll();

    List<Community> getAllBySubscribersNotContains(User user);

    List<Community> getAllBySubscribersContains(User user);

    List<Community> getAllByTitleStartsWith(String value);

    List<Community> getAllByTitleStartsWithAndSubscribersNotContains(String value, User user);

    List<Community> getAllByTitleStartsWithAndSubscribersContains(String value, User user);

}