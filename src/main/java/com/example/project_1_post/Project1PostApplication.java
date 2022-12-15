package com.example.project_1_post;

import com.example.project_1_post.dto.PostingRequestDto;
import com.example.project_1_post.repository.PostRepository;
import com.example.project_1_post.service.PostService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaAuditing
@EnableScheduling
public class Project1PostApplication {

    public static void main(String[] args) {
            SpringApplication.run(Project1PostApplication.class, args);
        }

    }
