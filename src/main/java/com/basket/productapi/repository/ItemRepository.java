package com.basket.productapi.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.basket.productapi.entity.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {
	Optional<Item> findByProductId(Long productId);
}
