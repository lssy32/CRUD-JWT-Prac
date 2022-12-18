package com.example.project_1_post.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    private String username;
    private String password;
    private boolean admin = false;
    private String adminToken = "";
}
