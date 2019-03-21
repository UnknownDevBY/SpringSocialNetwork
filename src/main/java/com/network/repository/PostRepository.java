package com.network.repository;

import com.network.model.Community;
import com.network.model.Post;
import com.network.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PostRepository extends CrudRepository<Post, Integer> {

    List<Post> getByOwnerOrderByPostTimeAsc(User user);

    List<Post> getByCommunityOrderByPostTimeAsc(Community community);

    Post getById(int id);
}
