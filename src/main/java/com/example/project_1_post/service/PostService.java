package com.example.project_1_post.service;

import com.example.project_1_post.dto.PostingRequestDto;
import com.example.project_1_post.entity.Post;
import com.example.project_1_post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    @Transactional
    public Post createPost(PostingRequestDto postingRequestDto){
        Post post = new Post(postingRequestDto);
        postRepository.save(post);
        return post;
    }

    @Transactional(readOnly = true)
    public List<Post> getAllPosts(){
        return postRepository.findAllByOrderByModifiedAtDesc();
    }

    @Transactional
    public Long update(Long id, PostingRequestDto postingRequestDto){
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );
        post.update(postingRequestDto);
        return post.getPassword();
    }

    @Transactional
    public Long deletePost(Long id){

        postRepository.deleteById(id);
        return id;
    }
}
