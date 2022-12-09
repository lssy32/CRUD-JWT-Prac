package com.example.project_1_post.service;

import com.example.project_1_post.dto.PasswordOnlyDto;
import com.example.project_1_post.dto.PostingRequestDto;
import com.example.project_1_post.entity.Post;
import com.example.project_1_post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.springframework.http.RequestEntity.post;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    @Transactional
    public Post createPost(PostingRequestDto postingRequestDto) {
        Post post = new Post(postingRequestDto);
        postRepository.save(post);
        return post;
    }

    @Transactional(readOnly = true)
    public List<Post> getAllPosts() {
        return postRepository.findAllByOrderByModifiedAtDesc();
    }

    @Transactional
    public Post update(Long id, PostingRequestDto postingRequestDto) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );
        if (post.getPassword() == postingRequestDto.getPassword()) {
            post.update(postingRequestDto);
        }
        return post;
    }

    @Transactional
    public String deletePost(Long id, PasswordOnlyDto passwordOnlyDto) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );
        if (post.getPassword() != passwordOnlyDto.getPassword()){
            return "비밀번호가 틀립니다.";
        } else if (post.getPassword() == passwordOnlyDto.getPassword()) {
            postRepository.deleteById(id);
            return "삭제 성공";
        } else {
            return "저는 뭘까요?";
        }

        }
    }
