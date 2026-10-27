package com.portfolio.my_first_toyproject.common.auth.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.portfolio.my_first_toyproject.common.auth.jwt.dto.LoginRequest;
import com.portfolio.my_first_toyproject.common.auth.jwt.dto.TokenInfoDTO;
import com.portfolio.my_first_toyproject.common.auth.jwt.service.AuthService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController 
@RequestMapping (value = "/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<TokenInfoDTO> login(@RequestBody LoginRequest request) {
        //TODO: process POST request
        TokenInfoDTO tokenInfo = authService.login(request.email(), request.password());
        
        return ResponseEntity.ok(tokenInfo);
    }
    
}
