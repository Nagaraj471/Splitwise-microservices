package com.splitwise.authservice.controllers;

import com.splitwise.authservice.dtos.LoginRequest;
import com.splitwise.authservice.dtos.LoginResponse;
import com.splitwise.authservice.dtos.RegisterRequest;
import com.splitwise.authservice.dtos.RegisterResponse;
import com.splitwise.authservice.services.AuthServices;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthServices authServices;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(
            @Valid @RequestBody RegisterRequest request
            ){
        RegisterResponse response=authServices.register(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody LoginRequest request
            ){
        return ResponseEntity.ok(authServices.login(request));
    }
}
