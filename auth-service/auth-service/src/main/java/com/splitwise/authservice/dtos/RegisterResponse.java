package com.splitwise.authservice.dtos;

public record RegisterResponse(
        Long id,
        String email,
        String role,
        String message
) {
}
