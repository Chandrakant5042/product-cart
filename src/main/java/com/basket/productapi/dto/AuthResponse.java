package com.basket.productapi.dto;

public record AuthResponse(
        String accessToken,
        String refreshToken
) {}
