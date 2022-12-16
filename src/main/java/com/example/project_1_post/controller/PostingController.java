package com.example.project_1_post.controller;

import com.example.project_1_post.dto.PasswordOnlyDto;
import com.example.project_1_post.dto.PostingRequestDto;
import com.example.project_1_post.entity.Post;
import com.example.project_1_post.service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
    public Post createPost(@RequestBody PostingRequestDto RequestDto, HttpServletRequest request) {
        return postService.createPost(RequestDto, request);
    }

    @GetMapping("api/allposts")
    public List<Post> getAllPosts() {
        return postService.getAllPosts();
    }

   @GetMapping("api/posts/{id}")
    public Optional<Post> getPost(@PathVariable Long id) {
        return postService.getPost(id);
    }

    @PutMapping("/api/posts/{id}")
    public Post updatePost(@PathVariable Long id, @RequestBody PostingRequestDto postingRequestDto, HttpServletRequest request){
        return postService.update(id, postingRequestDto, request);
    }

  @DeleteMapping("/api/posts/del/{id}")
    public Map deletePost(@PathVariable Long id, HttpServletRequest request) {
        postService.deletePost(id, request);
      Map map = new HashMap<>();
      map.put("msg", "게시물 삭제 성공");
      map.put("statusCode", "200");
      return map;
    }
}
