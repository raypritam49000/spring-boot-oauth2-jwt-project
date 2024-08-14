package com.spring.oauth2.jwt.rest.api.dto;

import com.spring.oauth2.jwt.rest.api.model.User;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserDTO {
    private String id;
    private String username;

    public static UserDTO from(User user) {
        return UserDTO.builder().id(user.getId()).username(user.getUsername()).build();
    }
} 