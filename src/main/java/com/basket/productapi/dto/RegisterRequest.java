package com.basket.productapi.dto;

public record RegisterRequest(
        String username,
        String password,
        String role
) {}