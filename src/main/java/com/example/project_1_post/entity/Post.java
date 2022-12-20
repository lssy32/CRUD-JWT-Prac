package com.example.project_1_post.entity;

import com.example.project_1_post.dto.PostingRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


@Entity
@Getter
@NoArgsConstructor
public class Post extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String content;


    @OneToMany(fetch = FetchType.LAZY)
    private List<Comment> comments = new ArrayList<>();

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
    public void addCommentList(Comment comment){
        this.comments.add(comment);
    }
    public void sortCommentList(){
        comments.sort(Comparator.comparing(Timestamped::getModifiedAt));
    }

    public void update(PostingRequestDto postingRequestDto) {
        this.title = postingRequestDto.getTitle();
        this.content = postingRequestDto.getContent();
    }

    public static boolean isSameNamePost(Post post, User user){
        if (post.getUsername().equals(user.getUsername())){
            return true;
        }else {
            return false;
        }
    }
}
