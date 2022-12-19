package com.example.project_1_post.service;

import com.example.project_1_post.dto.PasswordOnlyDto;
import com.example.project_1_post.dto.PostingRequestDto;
import com.example.project_1_post.entity.Post;
import com.example.project_1_post.entity.User;
import com.example.project_1_post.jwt.JwtUtil;
import com.example.project_1_post.repository.PostRepository;
import com.example.project_1_post.repository.UserRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.RequestEntity.post;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Transactional
    public Post createPost(PostingRequestDto postingRequestDto, HttpServletRequest request) {
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
            String username = user.getUsername();
            Post post = new Post(username, postingRequestDto);
            postRepository.save(post);
            return post;

        } else {
            return null;
        }
    }

    @Transactional(readOnly = true)
    public List<Post> getAllPosts() {
        return postRepository.findAllByOrderByModifiedAtDesc();
    }

    @Transactional(readOnly = true)
    public Optional<Post> getPost(Long id) {
        return postRepository.findById(id);
    }

    @Transactional
    public Post update(Long id, PostingRequestDto postingRequestDto, HttpServletRequest request) {
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
            if (User.isAdmin(user)){
                post.update(postingRequestDto);
            } else if (Post.isSameName(post, user)) {
                post.update(postingRequestDto);
            }
            return post;
        } else {
            return null;
        }
    }


    @Transactional
    public void deletePost(Long id, HttpServletRequest request) {
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
            if (User.isAdmin(user)){
                postRepository.deleteById(id);
            } else if (Post.isSameName(post, user)) {
                postRepository.deleteById(id);
            }
        }
    }
}