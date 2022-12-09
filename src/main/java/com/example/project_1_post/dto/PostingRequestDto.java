package com.example.project_1_post.dto;

import lombok.Getter;

@Getter
public class PostingRequestDto {
    private String title;
    private String writer;
    private String text;
    private Long password;
}
