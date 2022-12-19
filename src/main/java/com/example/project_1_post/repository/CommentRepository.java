package com.example.project_1_post.repository;

import com.example.project_1_post.entity.Comment;
import com.example.project_1_post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByOrderByModifiedAtDesc();

}