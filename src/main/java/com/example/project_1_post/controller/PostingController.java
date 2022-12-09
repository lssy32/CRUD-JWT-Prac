package com.example.project_1_post.controller;

import com.example.project_1_post.dto.PasswordOnlyDto;
import com.example.project_1_post.dto.PostingRequestDto;
import com.example.project_1_post.entity.Post;
import com.example.project_1_post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public Post getPost(@PathVariable Long id, @RequestBody Post post) {
        return post;
    }

    @PutMapping("/api/posts/{id}")
    public Post updatePost(@PathVariable Long id, @RequestBody PostingRequestDto postingRequestDto){
        return postService.update(id, postingRequestDto);
    }

  @DeleteMapping("/api/posts/del/{id}")
    public String deletePost(@PathVariable Long id, @RequestBody PasswordOnlyDto passwordOnlyDto) {
        return postService.deletePost(id, passwordOnlyDto);
    }
}
