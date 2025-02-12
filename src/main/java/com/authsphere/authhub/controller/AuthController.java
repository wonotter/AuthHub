package com.authsphere.authhub.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.authsphere.authhub.dto.LoginRequest;
import com.authsphere.authhub.dto.SignUpRequest;
import com.authsphere.authhub.service.application.AuthService;
import com.nimbusds.oauth2.sdk.token.Tokens;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<Void> signUp(
        @Valid @RequestBody final SignUpRequest signUpRequest
    ) {
        authService.signUp(signUpRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public ResponseEntity<Tokens> login(
        @Valid @RequestBody final LoginRequest loginRequest
    ) {
        Tokens tokens = authService.login(loginRequest);
        return ResponseEntity.ok().body(tokens);
    }
}
