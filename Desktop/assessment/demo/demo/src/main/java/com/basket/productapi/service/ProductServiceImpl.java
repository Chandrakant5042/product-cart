package com.basket.productapi.service;


import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.basket.productapi.dto.ProductRequest;
import com.basket.productapi.dto.ProductResponse;
import com.basket.productapi.entity.Item;
import com.basket.productapi.entity.Product;
import com.basket.productapi.exception.ResourceNotFoundException;
import com.basket.productapi.repository.ItemRepository;
import com.basket.productapi.repository.ProductRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

	@Autowired
    private ProductRepository productRepository;
	
	@Autowired
	private ItemRepository itemRepository;

    @Override
    public Page<ProductResponse> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable)
                .map(this::mapToResponse);
    }

    @Override
    public ProductResponse getProduct(Long id) {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        return mapToResponse(product);
    }

    public ProductResponse createProduct(ProductRequest request, String username) {

        Product product = new Product();
        product.setProductName(request.productName());
        product.setCreatedBy(username);
        product.setCreatedOn(LocalDateTime.now());

        // Create Item (stock entry)
        Item item = new Item();
        item.setQuantity(request.quantity());
        item.setProduct(product);   // VERY IMPORTANT

        // Set relationship in Product
        product.setItem(item);

        Product saved = productRepository.save(product);
        Item savedItem=itemRepository.save(item);
        return new ProductResponse(
                saved.getId(),
                saved.getProductName(),
                saved.getCreatedBy(),
                saved.getCreatedOn(),
                savedItem.getQuantity()
        );
    }

    private ProductResponse mapToResponse(Product p) {

        Integer quantity = null;

        if (p.getItem() != null) {
            quantity = p.getItem().getQuantity();
        }

        return new ProductResponse(
                p.getId(),
                p.getProductName(),
                p.getCreatedBy(),
                p.getCreatedOn(),
                quantity
        );
    }
    
    @Override
    public ProductResponse updateProduct(Long id,
                                         ProductRequest request,
                                         String username) {

        Product product = productRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product not found with id: " + id));
        
        Item item = itemRepository.findByProductId(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Item not found with id: " + id));

        Item item1 = itemRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product not found with id: " + id));
        
        item.setQuantity(request.quantity());
        
        product.setProductName(request.productName());
        product.setModifiedBy(username);
        
        product.setModifiedOn(LocalDateTime.now());
        System.out.println("dddddddddddddddddddd"+product.toString());
        Product updated = productRepository.save(product);
        Item savedItem=itemRepository.save(item);

        return mapToResponse(updated);
    }

    @Override
    public void deleteProduct(Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product not found with id: " + id));

        productRepository.delete(product);
    }
}
