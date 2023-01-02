package com.example.project_1_post.service;

import com.example.project_1_post.dto.PostsRequestDto;
import com.example.project_1_post.dto.PostsResponseDto;
import com.example.project_1_post.entity.Post;
import com.example.project_1_post.entity.User;
import com.example.project_1_post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.http.RequestEntity.post;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    @Transactional
    public PostsResponseDto createPost(PostsRequestDto postsRequestDto, String username){
            Post post = new Post(username, postsRequestDto);
            postRepository.save(post);
            return new PostsResponseDto(post);
    }

    @Transactional(readOnly = true)
    public List<PostsResponseDto> getAllPosts() {
        List<Post> postList = postRepository.findAllByOrderByModifiedAtDesc();
        List<PostsResponseDto> postsResponseDtoList = postList.stream().map(post -> new PostsResponseDto(post)).collect(Collectors.toList());
        return postsResponseDtoList; // postsResponseDtoList 안의 필드들을 public으로 바꾸니까 다 나옴
    }

    @Transactional(readOnly = true)
    public PostsResponseDto getPost(Long id) {
        Post post = postRepository.findById(id).get();
        return new PostsResponseDto(post);
    }

    @Transactional
    public Post updatePost(Long id, PostsRequestDto postsRequestDto, Post post, User user) {
            if (User.isAdmin(user)){
                post.update(postsRequestDto);
                return post;
            } else if (Post.isSameNamePost(post, user)) {
                post.update(postsRequestDto);
                return post;
            } else {
                throw new IllegalArgumentException("권한이 없습니다.");
            }

    }


    @Transactional
    public void deletePost(Long id, Post post, User user) {
            if (User.isAdmin(user)){
                postRepository.deleteById(id);
            } else if (Post.isSameNamePost(post, user)) {
                postRepository.deleteById(id);
            } else {
                throw new IllegalArgumentException("권한이 없습니다.");
            }
        }
    }