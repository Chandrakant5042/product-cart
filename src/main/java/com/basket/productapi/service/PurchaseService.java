package com.basket.productapi.service;

import java.util.List;

import com.basket.productapi.entity.Purchase;

public interface PurchaseService {

    void buyProduct(Long productId, String username);

    List<Purchase> getUserPurchases(String username);
}