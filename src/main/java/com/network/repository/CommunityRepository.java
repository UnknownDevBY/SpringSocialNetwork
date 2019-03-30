package com.network.repository;

import com.network.model.Community;
import com.network.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommunityRepository extends JpaRepository<Community, Integer> {

    Community getById(int id);
    List<Community> getAllBySubscribersNotContains(User user);
    List<Community> getAllBySubscribersContains(User user);
    List<Community> getAllByTitleStartsWithAndSubscribersNotContains(String value, User user);
    List<Community> getAllByTitleStartsWithAndSubscribersContains(String value, User user);
}