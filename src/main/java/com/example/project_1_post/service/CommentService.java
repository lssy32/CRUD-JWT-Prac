package com.example.project_1_post.service;

import com.example.project_1_post.dto.CommentDto;
import com.example.project_1_post.entity.Comment;
import com.example.project_1_post.entity.Post;
import com.example.project_1_post.entity.User;
import com.example.project_1_post.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    @Transactional
    public Comment createComment(CommentDto commentDto, User user, Post post) {
        Comment comment = new Comment(post.getId(), user.getUsername(), commentDto.getComment());
        commentRepository.save(comment);
        return comment;
    }
}

