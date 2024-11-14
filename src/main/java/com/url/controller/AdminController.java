package com.url.controller;

import com.url.model.User;
import com.url.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.getUserByRoleUser("USER");
    }

    @GetMapping("/allAdmin")
    public List<User> getAllAdmin() {
        return userService.getUserByRoleUser("ADMIN");
    }

    @GetMapping("/totalUsers")
    public List<HashMap<String, Integer>> getTotalUsers() {
        HashMap<String, Integer> response = new HashMap<>();
        List<User> users = userService.getUserByRoleUser("USER");
        response.put("totalUsers", users.size());
        return List.of(response);
    }

    @GetMapping("/totalAdmin")
    public List<HashMap<String, Integer>> getTotalAdmin() {
        HashMap<String, Integer> response = new HashMap<>();
        List<User> users = userService.getUserByRoleUser("ADMIN");
        response.put("totalAdmin", users.size());
        return List.of(response);
    }

    //how many url shortened
    // how many url shortened today
    // how many url shortened this week
    // how many url shortened this month
    // how many clicks
    // how many unique users
    // how many unique countries
    // how many unique browsers
    // how many unique operating systems
    // how many unique devices
    // how many clicks today
    // how many clicks this week
    // how many clicks this month
    // how many users created today
    // how many users created this week
    // how many users created this month

}
