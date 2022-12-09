package com.example.project_1_post.dto;

import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
public class PasswordOnlyDto {
    Long password;
}
