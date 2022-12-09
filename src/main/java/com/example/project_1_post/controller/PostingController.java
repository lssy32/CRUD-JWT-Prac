package com.example.project_1_post.controller;

import com.example.project_1_post.dto.PostingRequestDto;
import com.example.project_1_post.entity.Post;
import com.example.project_1_post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
public class PostingController {
    private final PostService postService;

    @GetMapping("/")
    @ResponseBody
    public String home() {
        String 안녕 = "안녕";
        return 안녕;
    }

    @PostMapping("api/newpost")
    public Post createPost(@RequestBody PostingRequestDto RequestDto) {
        return postService.createPost(RequestDto);
    }

    @GetMapping("api/allposts")
    public List<Post> getAllPosts() {
        return postService.getAllPosts();
    }

   @GetMapping("api/posts/{id}")
    public Post getPost(@PathVariable Long id, Post post) {
        return post;
    }

    @PutMapping("/api/posts/{id}")
    public Long updatePost(@PathVariable Long id, @RequestBody PostingRequestDto postingRequestDto){
        return postService.update(id, postingRequestDto);
    }

  @DeleteMapping("/api/posts/del/{id}")
    public Long deletePost(@PathVariable Long id) {
        return postService.deletePost(id);
    }
}
