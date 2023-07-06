package com.stefanini.pizzariabackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationFormDto {
    private String email;
    private String nickname;
    private String password;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String address;
}
