package com.basket.productapi.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.basket.productapi.entity.Product;
import com.basket.productapi.entity.Purchase;
import com.basket.productapi.entity.User;
import com.basket.productapi.repository.ProductRepository;
import com.basket.productapi.repository.PurchaseRepository;
import com.basket.productapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class PurchaseServiceImpl implements PurchaseService {

	private final ProductRepository productRepository;
	private final UserRepository userRepository;
	private final PurchaseRepository purchaseRepository;

	@Override
	public void buyProduct(Long productId, String username) {

		log.info("User {} buying product {}", username, productId);

		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new RuntimeException("Product not found"));

		User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));

		Optional<Purchase> existingPurchase = purchaseRepository.findByUserAndProduct(user, product);

		Purchase purchase;

		if (existingPurchase.isPresent()) {
			purchase = existingPurchase.get();
			purchase.setQuantity(purchase.getQuantity() + 1);
			purchase.setPurchasedAt(LocalDateTime.now());
		} else {
			purchase = new Purchase();
			purchase.setProduct(product);
			purchase.setUser(user);
			purchase.setQuantity(1);
			purchase.setPurchasedAt(LocalDateTime.now());
		}

		purchaseRepository.save(purchase);
	}

	@Override
	public List<Purchase> getUserPurchases(String username) {
		return purchaseRepository.findByUserUsername(username);
	}
}