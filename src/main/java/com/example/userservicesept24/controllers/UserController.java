package com.example.userservicesept24.controllers;

import com.example.userservicesept24.dtos.*;
import com.example.userservicesept24.dtos.ResponseStatus;
import com.example.userservicesept24.models.Token;
import com.example.userservicesept24.models.User;
import com.example.userservicesept24.services.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    //login, signUp, validateToken, logout
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public LoginResponseDto login(@RequestBody LoginRequestDto requestDto) {
        LoginResponseDto responseDto = new LoginResponseDto();

        try {
            Token token = userService.login(requestDto.getEmail(),
                    requestDto.getPassword());

            responseDto.setToken(token.getValue());
            responseDto.setResponseStatus(ResponseStatus.SUCCESS);
        } catch (Exception e) {
            responseDto.setResponseStatus(ResponseStatus.FAILURE);
        }

        return responseDto;
    }

    @PostMapping("/signup")
    public UserDto signUp(@RequestBody SignUpRequestDto requestDto) {
        User user = userService.signUp(
                requestDto.getName(),
                requestDto.getEmail(),
                requestDto.getPassword()
        );

        //Convert User into UserDto
        return UserDto.from(user);
    }

    @PatchMapping("/logout")
    public void logout(@RequestBody LogoutRequestDto requestDto) {

    }

    @GetMapping("/validate")
    public UserDto validateToken(String token) {
        User user = userService.validateToken(token);
        return UserDto.from(user);
    }

}
