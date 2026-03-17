package com.basket.productapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.basket.productapi.entity.Item;
import com.basket.productapi.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
	

}

