package com.basket.productapi.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.basket.productapi.dto.ProductRequest;
import com.basket.productapi.dto.ProductResponse;
import com.basket.productapi.entity.Product;
import com.basket.productapi.repository.ProductRepository;
import com.basket.productapi.service.ProductService;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

	private static final Logger log = LoggerFactory.getLogger(ProductController.class);
	
	@Autowired
    private ProductService productService;
	@Autowired
	private ProductRepository productRepository;

    
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllProducts(
            @PageableDefault(size = 3) Pageable pageable) {

        Page<ProductResponse> page = productService.getAllProducts(pageable);

        Map<String, Object> response = new HashMap<>();
        response.put("content", page.getContent());
        response.put("page", page.getNumber());
        response.put("size", page.getSize());
        response.put("totalElements", page.getTotalElements());
        response.put("totalPages", page.getTotalPages());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ProductResponse getById(@PathVariable Long id) {
    	log.info("Fetching product with id {}", id);
        return productService.getProduct(id);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductResponse> create(
            @Valid @RequestBody ProductRequest request,
            Authentication authentication) {

    	log.info("Create product request received");

        log.info("Logged in user: {}", authentication.getName());
        log.info("User authorities: {}", authentication.getAuthorities());
//        System.out.println("Auth object: " + authentication);
//        System.out.println("Username: " + authentication.getName());
//        System.out.println("Authorities: " + authentication.getAuthorities());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(productService.createProduct(request, authentication.getName()));
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody ProductRequest request,
            Authentication authentication) {

        ProductResponse response =
                productService.updateProduct(id, request, authentication.getName());

        return ResponseEntity.ok(response);
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        productService.deleteProduct(id);

        return ResponseEntity.noContent().build();
    }
    
    
}