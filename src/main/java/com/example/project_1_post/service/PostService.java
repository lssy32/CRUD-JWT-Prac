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

import java.util.ArrayList;
import java.util.List;

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
                String username = claims.getSubject();
                User user = userRepository.findByUsername(username).orElseThrow(
                        () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
                );

                Post post = new Post(user.getUsername(), postingRequestDto);
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

//    @Transactional
//    public Post update(Long id, PostingRequestDto postingRequestDto) {
//        Post post = postRepository.findById(id).orElseThrow(
//                () -> new IllegalArgumentException("게시글이 존재하지 않습니다.")
//        );
//        if (post.getPassword().equals(postingRequestDto.getPassword())) {
//            post.update(postingRequestDto);
//        }
//        return post;
//    }

//    @Transactional
//    public String deletePost(Long id, PasswordOnlyDto passwordOnlyDto) {
//        Post post = postRepository.findById(id).orElseThrow(
//                () -> new IllegalArgumentException("게시글이 존재하지 않습니다.")
//        );
//        if (post.getPassword().equals(passwordOnlyDto.getPassword())){
//            postRepository.deleteById(id);
//            return "삭제 성공";
//        } else {
//            return "비밀번호가 틀립니다.";
//        }
//
//        }
    }
