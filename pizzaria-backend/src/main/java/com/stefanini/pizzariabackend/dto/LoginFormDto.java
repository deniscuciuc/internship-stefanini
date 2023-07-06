package com.stefanini.pizzariabackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoginFormDto {
    private String email;
    private String nickname;
    private String password;
}
