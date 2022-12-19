package com.example.project_1_post.controller;

import com.example.project_1_post.dto.CommentDto;
import com.example.project_1_post.entity.Comment;
import com.example.project_1_post.entity.Post;
import com.example.project_1_post.entity.User;
import com.example.project_1_post.jwt.JwtUtil;
import com.example.project_1_post.repository.CommentRepository;
import com.example.project_1_post.repository.PostRepository;
import com.example.project_1_post.repository.UserRepository;
import com.example.project_1_post.service.CommentService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentService commentService;
    private final CommentRepository commentRepository;

    @PostMapping("api/{id}/newcomment")
    public Comment createComment(@PathVariable Long id, @RequestBody CommentDto commentDto, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request); // 토큰 꺼내기
        Claims claims; // 이건 모지

        if (token != null) {
            // Token 검증
            if (jwtUtil.validateToken(token)) {
                // 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("토큰이 일치하지 않습니다.");
            }
            // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );
            Post post = postRepository.findById(id).orElseThrow(
                    () -> new IllegalArgumentException("게시글이 존재하지 않습니다.")
            );
            return commentService.createComment(commentDto, user, post);
        } else {
            throw new IllegalArgumentException("토큰이 없습니다.");
        }
    }

    @PutMapping("/api/comments/{id}")
    public Comment updateComment(@PathVariable Long id, @RequestBody CommentDto commentDto, HttpServletRequest request) {
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
                    () -> new IllegalArgumentException("게시글이 존재하지 않습니다."));

            Comment comment = commentRepository.findById(id).orElseThrow(
                    () -> new IllegalArgumentException("댓글이 존재하지 않습니다.")
            );
            return commentService.updateComment(id, commentDto, comment, user, post);
        } else {
            throw new IllegalArgumentException("토큰이 없습니다.");
        }
    }

    @DeleteMapping("/api/comments/del/{id}")
    public Map deleteComment(@PathVariable Long id, HttpServletRequest request) {
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
                    () -> new IllegalArgumentException("게시글이 존재하지 않습니다."));

            Comment comment = commentRepository.findById(id).orElseThrow(
                    () -> new IllegalArgumentException("댓글이 존재하지 않습니다.")
            );
            commentService.deleteComment(id, comment, user, post);
            Map map = new HashMap<>();
            map.put("msg", "게시물 삭제 성공");
            map.put("statusCode", "200");
            return map;
        } else {
            throw new IllegalArgumentException("토큰이 없습니다.");
        }
    }

}
