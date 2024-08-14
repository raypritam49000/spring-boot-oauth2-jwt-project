package com.spring.oauth2.jwt.rest.api.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUp {
    private String username;
    private String password;
} 
