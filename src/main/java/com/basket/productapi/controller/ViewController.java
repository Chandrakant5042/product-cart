package com.basket.productapi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

	@GetMapping("/login-page")
	public String loginPage() {
		return "login";
	}

	@GetMapping("/signup-page")
	public String signupPage() {
		return "signup";
	}

	@GetMapping("/products-page")
	public String productsPage() {
		return "products";
	}

	@GetMapping("/create-product-page")
	public String createProductPage() {
		return "create-product";
	}

	@GetMapping("/edit-product-page")
	public String editProductPage() {
		return "edit-product";
	}

	@GetMapping("/my-products-page")
	public String myProductsPage() {
		return "my-products";
	}

	@GetMapping("/profile-page")
	public String profilePage() {
		return "profile";
	}
}