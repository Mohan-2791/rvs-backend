package com.rvs.backend.dto;

import com.rvs.backend.enums.Role;
import lombok.Data;

@Data
public class RegisterRequest {
    private String username;
    private String email;
    private String password;
    private Role role; // CLIENT, EMPLOYEE, or ADMIN
    
    // Optional initial profile fields for clients
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String address;
}