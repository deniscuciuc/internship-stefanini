package com.stefanini.librarybackend.controller;

import com.stefanini.librarybackend.service.JWTTokenService;
import com.stefanini.librarybackend.service.impl.JWTTokenServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/token")
public class JWTTokenController {

    private final JWTTokenService JWTTokenService;

    public JWTTokenController(JWTTokenServiceImpl JWTTokenServiceImpl) {
        this.JWTTokenService = JWTTokenServiceImpl;
    }

    @GetMapping("/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) {
        JWTTokenService.refreshToken(request, response);
    }
}
