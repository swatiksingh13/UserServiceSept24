package com.example.userservicesept24.dtos;

import com.example.userservicesept24.models.Token;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponseDto {
    private String token;
    private ResponseStatus responseStatus;
}
