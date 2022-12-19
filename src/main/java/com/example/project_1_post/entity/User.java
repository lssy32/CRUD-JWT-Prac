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
    @Pattern(regexp= "^[A-Za-z0-9$@$!%*?&]{8,15}$", message = "8~15자리로 입력해주세요 (영어 대/소문자, 특수문자, 숫자 사용 가능")
    private String password;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;


    public User(String username, String password, UserRoleEnum role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public static boolean isAdmin(User user) {
     if(user.getRole().equals(UserRoleEnum.ADMIN)){
         return true;
     }else{
         return false;
     }
    }
}