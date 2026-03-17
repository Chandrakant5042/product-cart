package com.basket.productapi.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProductRequest( 
		@NotBlank(message = "Product name is required")
		String productName, 
		@NotNull(message = "Quantity is required")
		@Min(value = 0, message = "Quantity cannot be negative")
		Integer quantity)
{}
