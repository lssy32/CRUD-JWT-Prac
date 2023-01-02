package com.example.project_1_post.entity;

import com.example.project_1_post.dto.CommentRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Comment extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Post_Id", nullable = false)
    private Post post;

    public Comment(User user, CommentRequestDto commentRequestDto, Post post)  {
        this.username = user.getUsername();
        this.content = commentRequestDto.getContent();
        this.post = post;
    }

    public void update(CommentRequestDto commentRequestDto) {
        this.content = commentRequestDto.getContent();
    }

    public static boolean isSameNameComment(Comment comment, User user) {
        if (comment.getUsername().equals(user.getUsername())) {
            return true;
        } else {
            return false;
        }
    }
}
