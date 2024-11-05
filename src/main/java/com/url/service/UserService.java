package com.url.service;

import com.url.model.User;

import java.util.List;

public interface UserService {
    User saveUser(User user);
    User getUserById(String userId);
    User updateUserById(String userId, User user);
    void deleteUserById(String userId);
    List<User> getAllUsers();
    User getUserByEmail(String email);
}
