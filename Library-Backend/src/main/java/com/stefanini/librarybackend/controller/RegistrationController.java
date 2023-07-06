package com.stefanini.librarybackend.controller;

import com.stefanini.librarybackend.dto.LoginRequestDto;
import com.stefanini.librarybackend.dto.RegistrationRequestDto;
import com.stefanini.librarybackend.service.RegistrationService;
import com.stefanini.librarybackend.service.impl.AppUserServiceImpl;
import com.stefanini.librarybackend.service.impl.RegistrationServiceImpl;
import com.stefanini.librarybackend.service.impl.exception.EmailAlreadyTakenException;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class RegistrationController {

    private final RegistrationService registrationService;
    private final AppUserServiceImpl appUserServiceImpl;

    public RegistrationController(RegistrationServiceImpl registrationServiceImpl, @Lazy AppUserServiceImpl appUserServiceImpl) {
        this.registrationService = registrationServiceImpl;
        this.appUserServiceImpl = appUserServiceImpl;
    }


    @PostMapping(value = "/sign-up")
    public ResponseEntity<?> signUp(@RequestBody RegistrationRequestDto request) {
        try {
            registrationService.registerUser(request);
            return ResponseEntity
                    .status(HttpStatus.ACCEPTED)
                    .body(appUserServiceImpl.login(new LoginRequestDto(request.getEmail(), request.getPassword())));
        } catch (EmailAlreadyTakenException e) {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body("Email is already taken");
        }
    }
}
