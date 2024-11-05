package com.url.controller.authController;


import com.url.model.LoginDto;
import com.url.model.RegisterDto;
import com.url.model.User;
import com.url.service.UserService;
import com.url.util.JwtUtil;
import com.url.util.UserDetailsServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/healthCheck")
    public HashMap<String, String> healthCheck() {
        HashMap<String, String> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "Server is running");
        return response;
    }
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginDto loginDto) {
        System.out.println(loginDto.getEmail());
        System.out.println(loginDto.getPassword());
        String email = loginDto.getEmail();
        String password = loginDto.getPassword();

        Map<String, String> response = new HashMap<>();

        if (email == null || password == null) {
            response.put("status", "error");
            response.put("message", "All fields are required");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        User user = userService.getUserByEmail(email);


        if (user == null) {
            response.put("status", "error");
            response.put("message", "User not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password));
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);
            System.out.println(userDetails.toString());
            String jwt = jwtUtil.generateToken(userDetails.getUsername());
            response.put("status", "success");
            response.put("message", "User logged in successfully");
            response.put("token", jwt);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (BadCredentialsException e) {
            response.put("status", "error");
            response.put("message", "Invalid username or password");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
    }


    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody RegisterDto registerDto) {
        Map<String, String> response = new HashMap<>();


        if (registerDto == null){
            response.put("status", "error");
            response.put("message", "All fields are required");
            return ResponseEntity.ok(response);
        }


        if (registerDto.getFirstName() == null || registerDto.getLastName() == null || registerDto.getEmail() == null || registerDto.getPassword() == null) {
            response.put("status", "error");
            response.put("message", "All fields are required");
            return ResponseEntity.ok(response);
        }

        if (userService.getUserByEmail(registerDto.getEmail()) != null) {
            response.put("status", "error");
            response.put("message", "User already exists");
            return ResponseEntity.ok(response);
        }

        User user = new User();
        user.setFirstName(registerDto.getFirstName());
        user.setLastName(registerDto.getLastName());
        user.setEmail(registerDto.getEmail());
        user.setPassword(registerDto.getPassword());



        User savedUser = userService.saveUser(user);
        if (savedUser != null) {
            response.put("status", "success");
            response.put("message", "User registered successfully");
        } else {
            response.put("status", "error");
            response.put("message", "Failed to register user");
        }

        return ResponseEntity.ok(response);
    }

}
