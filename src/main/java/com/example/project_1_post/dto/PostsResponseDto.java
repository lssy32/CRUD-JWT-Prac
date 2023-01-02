package com.example.project_1_post.dto;

import com.example.project_1_post.entity.Comment;
import com.example.project_1_post.entity.Post;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Setter
public class PostsResponseDto {
    public Long id;
    public LocalDateTime createdAt;
    public LocalDateTime modifiedAt;
    public String title;
    public String username;
    public String content;
    public List<CommentResponseDto> comments = new ArrayList<>();

    public PostsResponseDto(Post post) {
        this.id = post.getId();
        this.createdAt = post.getCreatedAt();
        this.modifiedAt = post.getModifiedAt();
        this.title = post.getTitle();
        this.username = post.getUsername();
        this.content = post.getContent();
        List<CommentResponseDto> list = new ArrayList<>();
        for (Comment comment : post.getComments()){
            list.add(new CommentResponseDto(comment));
        }
        this.comments = list;
    }
}
