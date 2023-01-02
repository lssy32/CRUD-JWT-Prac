package com.example.project_1_post.dto;

import lombok.Getter;

@Getter
public class UserRequestDto {
    private String username;
    private String password;
    private boolean admin = false;
    private String adminToken = "";
}
