package com.example.project_1_post.controller;

import com.example.project_1_post.dto.AuthenticatedUser;
import com.example.project_1_post.dto.PostsRequestDto;
import com.example.project_1_post.dto.PostsResponseDto;
import com.example.project_1_post.entity.Post;
import com.example.project_1_post.entity.User;
import com.example.project_1_post.jwt.JwtUtil;
import com.example.project_1_post.repository.PostRepository;
import com.example.project_1_post.repository.UserRepository;
import com.example.project_1_post.service.PostService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class PostsController {
    private final PostService postService;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @GetMapping("/")
    @ResponseBody
    public String home() {
        String 안녕 = "안녕";
        return 안녕;
    }

    @PostMapping("api/newpost")
    public PostsResponseDto createPost(@RequestBody PostsRequestDto postsRequestDto, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request); // 토큰 꺼내기
        Claims claims; // 이건 모지
        AuthenticatedUser authenticatedUser = jwtUtil.validateAndGetInfo(token);
        if (token != null) {
            // Token 검증
            if (jwtUtil.validateToken(token)) {
                // 토큰에서 사용자 정보 가져오기
                authenticatedUser = jwtUtil.validateAndGetInfo(token);
            } else {
                throw new IllegalArgumentException("토큰이 일치하지 않습니다.");
            }
            // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
            User user = userRepository.findByUsername(authenticatedUser.getUsername()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );
            return postService.createPost(postsRequestDto, authenticatedUser.getUsername());
        } else {
            throw new IllegalArgumentException("토큰이 없습니다.");
        }

    }

    @GetMapping("api/allposts")
    public List<PostsResponseDto> getAllPosts() {
        return postService.getAllPosts();
    }

    @GetMapping("api/posts/{id}")
    public PostsResponseDto getPost(@PathVariable Long id) {
        return postService.getPost(id);
    }

    @PutMapping("/api/posts/{id}")
    public Post updatePost(@PathVariable Long id, @RequestBody PostsRequestDto postsRequestDto, HttpServletRequest request) {
        // Request에서 Token 가져오기
        String token = jwtUtil.resolveToken(request); // 토큰 꺼내기
        Claims claims; // 이건 모지

        if (token != null) {
            // Token 검증
            if (jwtUtil.validateToken(token)) {
                // 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }
            // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );

            Post post = postRepository.findById(id).orElseThrow(
                    () -> new IllegalArgumentException("게시글이 존재하지 않습니다.")
            );
            return postService.updatePost(id, postsRequestDto, post, user);
        } else {
            throw new IllegalArgumentException("토큰이 없습니다.");
        }
    }

    @DeleteMapping("/api/posts/del/{id}")
    public Map deletePost(@PathVariable Long id, HttpServletRequest request) {
        // Request에서 Token 가져오기
        String token = jwtUtil.resolveToken(request); // 토큰 꺼내기
        Claims claims; // 이건 모지

        if (token != null) {
            // Token 검증
            if (jwtUtil.validateToken(token)) {
                // 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }
            // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );

            Post post = postRepository.findById(id).orElseThrow(
                    () -> new IllegalArgumentException("게시글이 존재하지 않습니다.")
            );
            postService.deletePost(id, post, user);
            Map map = new HashMap<>();
            map.put("msg", "게시물 삭제 성공");
            map.put("statusCode", "200");
            return map;
        } else {
            throw new IllegalArgumentException("토큰이 없습니다.");
        }
    }
}
