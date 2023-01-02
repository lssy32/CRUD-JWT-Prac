package com.example.project_1_post.entity;

import com.example.project_1_post.dto.PostsRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
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
    @OrderBy(value = "modifiedAt desc")
    @OneToMany(fetch = FetchType.LAZY)
    private List<Comment> comments = new ArrayList<>();

    public Post(String username, PostsRequestDto postsRequestDto) {
        this.username = username;
        this.title = postsRequestDto.getTitle();
        this.content = postsRequestDto.getContent();
    }
    public void addCommentList(Comment comment){
        this.comments.add(comment);
    }
    public void sortCommentList(){
        comments.sort(Comparator.comparing(Timestamped::getModifiedAt));
    }

    public void update(PostsRequestDto postsRequestDto) {
        this.title = postsRequestDto.getTitle();
        this.content = postsRequestDto.getContent();
    }

    public static boolean isSameNamePost(Post post, User user){
        if (post.getUsername().equals(user.getUsername())){
            return true;
        }else {
            return false;
        }
    }
}
