package com.basket.productapi.service;

import java.time.LocalDateTime;
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
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

	private final ProductRepository productRepository;
	private final ItemRepository itemRepository;

	@Override
	public Page<ProductResponse> getAllProducts(Pageable pageable) {
		return productRepository.findAll(pageable).map(this::mapToResponse);
	}

	@Override
	public ProductResponse getProduct(Long id) {
		Product product = productRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
		return mapToResponse(product);
	}

	@Override
	public ProductResponse createProduct(ProductRequest request, String username) {
		Product product = new Product();
		product.setProductName(request.productName());
		product.setCreatedBy(username);
		product.setCreatedOn(LocalDateTime.now());

		Item item = new Item();
		item.setQuantity(request.quantity());
		item.setProduct(product);
		product.setItem(item);

		Product savedProduct = productRepository.save(product);
		return mapToResponse(savedProduct);
	}

	@Override
	public ProductResponse updateProduct(Long id, ProductRequest request, String username) {
		Product product = productRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));

		Item item = itemRepository.findByProductId(id)
				.orElseThrow(() -> new ResourceNotFoundException("Item not found for product id: " + id));

		product.setProductName(request.productName());
		product.setModifiedBy(username);
		product.setModifiedOn(LocalDateTime.now());
		item.setQuantity(request.quantity());

		productRepository.save(product);
		return mapToResponse(product);
	}

	@Override
	public void deleteProduct(Long id) {
		Product product = productRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
		productRepository.delete(product);
	}

	private ProductResponse mapToResponse(Product product) {
		Integer quantity = product.getItem() != null ? product.getItem().getQuantity() : null;
		return new ProductResponse(product.getId(), product.getProductName(), product.getCreatedBy(),
				product.getCreatedOn(), quantity);
	}
}