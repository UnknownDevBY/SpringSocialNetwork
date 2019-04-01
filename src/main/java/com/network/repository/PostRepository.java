package com.network.repository;

import com.network.model.Community;
import com.network.model.Post;
import com.network.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {

    List<Post> getByOwnerOrderByPostTimeDesc(User user);
    List<Post> getByCommunityOrderByPostTimeDesc(Community community);
    List<Post> getByContentContains(String content);
    Post getById(int id);
}
