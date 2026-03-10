package com.basket.productapi.service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.basket.productapi.dto.ProductRequest;
import com.basket.productapi.dto.ProductResponse;

public interface ProductService {

    Page<ProductResponse> getAllProducts(Pageable pageable);

    ProductResponse getProduct(Long id);

    ProductResponse createProduct(ProductRequest request, String username);

    ProductResponse updateProduct(Long id, ProductRequest request, String username);

    void deleteProduct(Long id);
}