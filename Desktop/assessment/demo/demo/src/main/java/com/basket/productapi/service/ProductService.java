package com.basket.productapi.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.basket.productapi.dto.ProductRequest;
import com.basket.productapi.dto.ProductResponse;

public interface ProductService {
	
	public Page<ProductResponse> getAllProducts(Pageable pageable);

	public ProductResponse getProduct(Long id);
	
	 public ProductResponse createProduct(ProductRequest request, String username) ;
	 
	 public ProductResponse updateProduct(Long id, ProductRequest request, String username);

	public void deleteProduct(Long id);
}
