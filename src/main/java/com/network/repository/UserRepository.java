package com.network.repository;

import com.network.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {

    User getByEmail(String email);
    User getById(int id);
    List<User> getAllByNameStartsWith(String value);
    List<User> getAllBySurnameStartsWith(String value);
}
