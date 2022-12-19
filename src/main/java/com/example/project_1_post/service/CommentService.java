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
        Comment comment = new Comment(user.getUsername(), commentDto.getContent());
        post.addCommentList(comment);
        commentRepository.save(comment);
        return comment;
    }

    @Transactional
    public Comment updateComment(Long id, CommentDto commentDto, Comment comment, User user, Post post) {
        if (User.isAdmin(user)){
            comment.update(commentDto);
            return comment;
        } else if (Comment.isSameNameComment(comment, user)) {
            comment.update(commentDto);
            return comment;
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

