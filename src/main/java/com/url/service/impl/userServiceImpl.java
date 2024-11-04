package com.url.service.impl;

import com.url.model.User;
import com.url.repository.UserRepository;
import com.url.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class userServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User getUserById(String userId) {;
        return userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public User updateUserById(String userId, User user) {
        var existingUser = getUserById(userId);
        existingUser.setFirstName(user.getFirstName() != null ? user.getFirstName() : existingUser.getFirstName());
        existingUser.setLastName(user.getLastName() != null ? user.getLastName() : existingUser.getLastName());
        existingUser.setEmail(user.getEmail() != null ? user.getEmail() : existingUser.getEmail());
        existingUser.setPassword(user.getPassword() != null ? user.getPassword() : existingUser.getPassword());
        return userRepository.save(existingUser);
    }


    @Override
    public void deleteUserById(String userId) {
        var existingUser = getUserById(userId);
        userRepository.delete(existingUser);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
