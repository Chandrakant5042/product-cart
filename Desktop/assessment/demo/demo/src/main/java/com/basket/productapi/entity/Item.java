package com.basket.productapi.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "item",
       uniqueConstraints = {
           @UniqueConstraint(name = "uk_product_id", columnNames = "product_id")
       })
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer quantity;

    @OneToOne
    @JoinColumn(name = "product_id",
                nullable = false,
                unique = true)
    private Product product;

    // ===== Getters & Setters =====

    public Long getId() { return id; }

    public Integer getQuantity() { return quantity; }

    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public Product getProduct() { return product; }

    public void setProduct(Product product) { this.product = product; }
}