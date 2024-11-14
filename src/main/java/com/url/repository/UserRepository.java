package com.url.repository;

import com.url.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserRepository extends MongoRepository<User, String> {
    org.springframework.security.core.userdetails.User findByEmail(String email);
    User getUserByEmail(String email);
    List<User> findByRole(String role);
}
