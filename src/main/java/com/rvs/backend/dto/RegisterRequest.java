package com.rvs.backend.dto;

import lombok.Data;

@Data
public class RegisterRequest {
    private String username;
    private String email;
    private String password;

    // Optional initial profile fields for clients
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String address;
}