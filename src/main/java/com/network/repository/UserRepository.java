package com.network.repository;

import com.network.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Integer> {

    @Query("SELECT u.email FROM User u")
    List<String> getAllEmails();

    List<User> findAll();

    User getByEmail(String email);

    User getById(int id);

    @Query("SELECT u.name FROM User u WHERE u.id = ?1")
    String getNameById(int id);

    @Query("SELECT u.surname FROM User u WHERE u.id = ?1")
    String getSurnameById(int id);

    List<User> getAllByNameStartsWith(String value);

    List<User> getAllBySurnameStartsWith(String value);
}
