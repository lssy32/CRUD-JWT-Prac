package com.example.project_1_post.controller;

import com.example.project_1_post.dto.UserRequestDto;
import com.example.project_1_post.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("api/auth/signup")
    @ResponseBody
    public Map signup(@RequestBody UserRequestDto userRequestDto) {
        userService.signup(userRequestDto);
        Map map = new HashMap<>();
        map.put("msg", "회원가입 성공");
        map.put("statusCode", "200");
        return map;
    }

    @PostMapping("api/auth/login")
    @ResponseBody
    public Map login(@RequestBody UserRequestDto userRequestDto, HttpServletResponse response) {
        userService.login(userRequestDto, response);
        Map map = new HashMap<>();
        map.put("msg", "로그인 성공");
        map.put("statusCode", "200");
        return map;
    }
}

