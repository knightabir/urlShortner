package com.url.service.impl;

import com.url.model.User;
import com.url.repository.UserRepository;
import com.url.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class userServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User saveUser(User user) {
        User tempUser = new User();
        tempUser.setEmail(user.getEmail());
        tempUser.setFirstName(user.getFirstName());
        tempUser.setLastName(user.getLastName());
        // Decrypt the password then save to the database
        tempUser.setPassword(passwordEncoder.encode(user.getPassword()));


        return userRepository.save(tempUser);
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

    @Override
    public User getUserByEmail(String email) {
        return userRepository.getUserByEmail(email);
    }
}
