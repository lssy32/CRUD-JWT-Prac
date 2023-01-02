package com.example.project_1_post.dto;

import com.example.project_1_post.entity.UserRoleEnum;
import lombok.Getter;

@Getter
public class AuthenticatedUser {
    private final String username;
    private final UserRoleEnum role;

    public AuthenticatedUser(String username, UserRoleEnum role) {
        this.username = username;
        this.role = role;
    }
}
