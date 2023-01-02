package com.example.project_1_post.service;

import com.example.project_1_post.dto.UserRequestDto;
import com.example.project_1_post.entity.User;
import com.example.project_1_post.entity.UserRoleEnum;
import com.example.project_1_post.jwt.JwtUtil;
import com.example.project_1_post.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private static final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";
    @Transactional
    public void signup(UserRequestDto userRequestDto) {

        String username = userRequestDto.getUsername();
        String password = userRequestDto.getPassword();

        // 회원 중복 확인
        Optional<User> found = userRepository.findByUsername(username);
        if (found.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }

        // 사용자 ROLE 확인
        UserRoleEnum role = UserRoleEnum.USER;
        if (userRequestDto.isAdmin()) {
            if (!userRequestDto.getAdminToken().equals(ADMIN_TOKEN)) {
                throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능합니다.");
            }
            role = UserRoleEnum.ADMIN;
        }

        User user = new User(username, password, role);
        userRepository.save(user);

    }

    @Transactional
    public void login(UserRequestDto userRequestDto, HttpServletResponse response) {

        String username = userRequestDto.getUsername();
        String password = userRequestDto.getPassword();

        // 사용자 확인
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("등록된 사용자가 없습니다.")
        );
        // 비밀번호 확인
        if(!user.getPassword().equals(password)){
            throw  new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }


        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(username, user.getRole()));
    }
}
