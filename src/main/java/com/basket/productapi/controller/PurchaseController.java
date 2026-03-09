package com.basket.productapi.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.basket.productapi.entity.Purchase;
import com.basket.productapi.service.PurchaseService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/purchases")
@RequiredArgsConstructor
public class PurchaseController {

    private final PurchaseService purchaseService;

    @PostMapping("/{productId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> buyProduct(
            @PathVariable Long productId,
            Authentication authentication) {

        purchaseService.buyProduct(productId, authentication.getName());

        return ResponseEntity.ok("Product purchased");
    }

    @GetMapping("/my-products")
    @PreAuthorize("hasRole('USER')")
    public List<Purchase> myProducts(Authentication authentication) {

        return purchaseService.getUserPurchases(authentication.getName());
    }
}
