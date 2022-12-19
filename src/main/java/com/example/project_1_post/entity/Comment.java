package com.example.project_1_post.entity;

import com.example.project_1_post.dto.CommentDto;
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

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "Post_Id")
//    private Post post;

    public Comment(String username, String content)  {
        this.username = username;
        this.content = content;
//        this.post = post;
    }

    public void update(CommentDto commentDto) {
        this.content = commentDto.getContent();
    }

    public static boolean isSameNameComment(Comment comment, User user) {
        if (comment.getUsername().equals(user.getUsername())) {
            return true;
        } else {
            return false;
        }
    }
}
