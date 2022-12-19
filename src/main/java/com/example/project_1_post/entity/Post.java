package com.example.project_1_post.entity;

import com.example.project_1_post.dto.PostingRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Post extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String content;


    public Post(String title, String username, String content) {
        this.title = title;
        this.username = username;
        this.content = content;
    }

    public Post(String username, PostingRequestDto postingRequestDto) {
        this.username = username;
        this.title = postingRequestDto.getTitle();
        this.content = postingRequestDto.getContent();
    }

    public void update(PostingRequestDto postingRequestDto) {
        this.title = postingRequestDto.getTitle();
        this.content = postingRequestDto.getContent();
    }

    public static boolean isSameName(Post post, User user){
        if (post.getUsername().equals(user.getUsername())){
            return true;
        }else {
            return false;
        }
    }
}
