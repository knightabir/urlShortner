package com.url.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class User {

    @Id
    private String id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String role = "USER";

    public User() {
    }

    public User(String id, String firstName, String lastName, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }
}
