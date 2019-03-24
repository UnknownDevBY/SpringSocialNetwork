package com.network.repository;

import com.network.model.Hashtag;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface HashtagRepository extends CrudRepository<Hashtag, Integer> {
    List<Hashtag> getAllByContent(String hashtag);

    boolean existsByContent(String tag);
}
