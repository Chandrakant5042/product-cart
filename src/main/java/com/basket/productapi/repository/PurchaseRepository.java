package com.basket.productapi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.basket.productapi.entity.Product;
import com.basket.productapi.entity.Purchase;
import com.basket.productapi.entity.User;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

    List<Purchase> findByUserUsername(String username);
    
    Optional<Purchase> findByUserAndProduct(User user, Product product);

}
