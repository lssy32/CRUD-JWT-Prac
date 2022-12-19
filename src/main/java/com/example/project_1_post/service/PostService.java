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
    public Post createPost(PostingRequestDto postingRequestDto, User user) {
        // Request에서 Token 가져오기
            String username = user.getUsername();
            Post post = new Post(username, postingRequestDto);
            postRepository.save(post);
            return post;
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
    public Post update(Long id, PostingRequestDto postingRequestDto, Post post, User user) {
            if (User.isAdmin(user)){
                post.update(postingRequestDto);
            } else if (Post.isSameName(post, user)) {
                post.update(postingRequestDto);
            }
            return post;
    }


    @Transactional
    public void deletePost(Long id, Post post, User user) {
            if (User.isAdmin(user)){
                postRepository.deleteById(id);
            } else if (Post.isSameName(post, user)) {
                postRepository.deleteById(id);
            }
        }
    }