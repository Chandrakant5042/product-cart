package com.basket.productapi.dto;

import java.time.LocalDateTime;

public record ProductResponse(Long id, String productName, String createdBy, LocalDateTime createdOn,
		Integer quantity) {
}