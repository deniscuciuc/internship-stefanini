package com.stefanini.librarybackend.controller;

import com.stefanini.librarybackend.service.EmailConfirmationTokenService;
import com.stefanini.librarybackend.service.impl.EmailConfirmationTokenServiceImpl;
import com.stefanini.librarybackend.service.impl.exception.InvalidTokenException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/email-confirmation")
public class EmailConfirmationTokenController {

    private final EmailConfirmationTokenService emailConfirmationTokenService;

    public EmailConfirmationTokenController(EmailConfirmationTokenServiceImpl emailConfirmationTokenServiceImpl) {
        this.emailConfirmationTokenService = emailConfirmationTokenServiceImpl;
    }

    @GetMapping("/confirm/{token}")
    public ResponseEntity<?> confirmEmail(@PathVariable String token) {
        try {
            return ResponseEntity
                    .status(HttpStatus.ACCEPTED)
                    .body(emailConfirmationTokenService.confirmToken(token));
        } catch (InvalidTokenException e) {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(e.getMessage());
        }
    }

    @GetMapping("/send-new-token/{token}")
    public ResponseEntity<?> sendNewToken(@PathVariable String token) {
        try {
            return ResponseEntity
                    .status(HttpStatus.ACCEPTED)
                    .body(emailConfirmationTokenService.sendNewToken(token));
        } catch (InvalidTokenException e) {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(e.getMessage());
        }
    }
}
