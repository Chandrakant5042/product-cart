package com.basket.productapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.basket.productapi.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
	

}

