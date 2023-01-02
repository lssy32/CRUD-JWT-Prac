package com.example.project_1_post.service;

import com.example.project_1_post.dto.CommentRequestDto;
import com.example.project_1_post.dto.CommentResponseDto;
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
    public CommentResponseDto createComment(User user, CommentRequestDto commentRequestDto, Post post) {
        Comment comment = new Comment(user, commentRequestDto, post);
        commentRepository.save(comment);
        post.addCommentList(comment);
        return new CommentResponseDto(comment);
    }

    @Transactional
    public CommentResponseDto updateComment(Long id, CommentRequestDto commentRequestDto, Comment comment, User user, Post post) {
        if (User.isAdmin(user)) {
            comment.update(commentRequestDto);
            post.sortCommentList();
            return new CommentResponseDto(comment);
        } else if (Comment.isSameNameComment(comment, user)) {
            comment.update(commentRequestDto);
            post.sortCommentList();
            return new CommentResponseDto(comment);
        } else {
            throw new IllegalArgumentException("권한이 없습니다.");
        }
    }

    @Transactional
    public void deleteComment(Long id, Comment comment, User user, Post post) {
        if (User.isAdmin(user)){
            commentRepository.deleteById(id);
        } else if (Comment.isSameNameComment(comment, user)) {
            commentRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("권한이 없습니다.");
        }
    }
}

