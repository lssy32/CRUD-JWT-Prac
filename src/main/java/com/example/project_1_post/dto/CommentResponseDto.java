package com.example.project_1_post.dto;

import com.example.project_1_post.entity.Comment;
import com.example.project_1_post.entity.Post;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CommentResponseDto {
    public Long id;
    public LocalDateTime createdAt;
    public LocalDateTime modifiedAt;
    public String username;
    public String content;
    // private Post post;

    public CommentResponseDto(Comment comment) {
        this.createdAt = comment.getCreatedAt();
        this.modifiedAt = comment.getModifiedAt();
        this.id = comment.getId();
        this.username = comment.getUsername();
        this.content = comment.getContent();
        // this.post = comment.getPost();
    }
}
