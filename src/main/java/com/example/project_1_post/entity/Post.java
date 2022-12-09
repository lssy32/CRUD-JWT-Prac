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
    private String writer;

    @Column(nullable = false)
    private Long password;

    @Column(nullable = false)
    private String text;

    public Post(String title, String writer, String text, Long password) {
        this.title = title;
        this.writer = writer;
        this.text = text;
        this.password = password;
    }

    public Post(PostingRequestDto postingRequestDto) {
        this.title = postingRequestDto.getTitle();
        this.writer = postingRequestDto.getWriter();
        this.text = postingRequestDto.getText();
        this.password = postingRequestDto.getPassword();
    }

    public void update(PostingRequestDto postingRequestDto) {
        this.title = postingRequestDto.getTitle();
        this.writer = postingRequestDto.getWriter();
        this.text = postingRequestDto.getText();
    }
}
