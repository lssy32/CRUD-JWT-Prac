package com.example.project_1_post.entity;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

@Getter
@NoArgsConstructor
@Entity(name = "users")
@Valid
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    @Pattern(regexp= "^[a-z0-9]{4,10}$", message = "4~10자리 영어 소문자와 숫자만 입력 가능합니다.")
    private String username;

    @Column(nullable = false)
    @Pattern(regexp= "^[a-z0-9]{8,15}$", message = "8~15자리 영어 소문자와 숫자만 입력 가능합니다.")
    private String password;



    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

}