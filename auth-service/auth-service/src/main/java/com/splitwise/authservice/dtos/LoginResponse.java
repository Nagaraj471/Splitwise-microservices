package com.splitwise.authservice.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginResponse(
        String accessToken,
        String tokenType,
        long expiresIn
) {
}