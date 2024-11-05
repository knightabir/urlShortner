package com.url.model;

import lombok.Data;

@Data
public class RegisterDto {

    private String email;
    private String password;
    private String firstName;
    private String lastName;
}
