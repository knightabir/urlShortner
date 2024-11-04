package com.url.controller.authController;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {


    @RequestMapping("/login")
    public String login() {
        return "login";
    }


    @RequestMapping("/register")
    public String register() {
        return "register";
    }

}