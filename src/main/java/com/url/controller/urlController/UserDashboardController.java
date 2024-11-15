package com.url.controller.urlController;

import com.url.model.User;
import com.url.service.URLService;
import com.url.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/url/dashboard")
public class UserDashboardController {

    @Autowired
    private URLService urlService;

    @Autowired
    private UserService userService;


    private User getCurrentUser(){
        log.debug("Fetching current user from security context");
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = userDetails.getUsername();
        return userService.getUserByEmail(email);
    }


    @GetMapping("/urls")
    public ResponseEntity<Object> getAllUrls(){
        User currentUser = getCurrentUser();
        return ResponseEntity.ok(urlService.getAllUrlsByUser(currentUser.getId()));
    }

    @GetMapping("/totalUrls")
    public ResponseEntity<Object> getTotalUrls(){
        User currentUser = getCurrentUser();
        return ResponseEntity.ok(urlService.getAllUrlsByUser(currentUser.getId()).size());
    }


}
