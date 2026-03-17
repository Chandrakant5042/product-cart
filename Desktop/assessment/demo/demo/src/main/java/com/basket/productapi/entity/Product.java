package com.basket.productapi.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "product",
       indexes = {
           @Index(name = "idx_product_name", columnList = "product_name")
       })
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(nullable = false)
    private String createdBy;

    @Column(nullable = false)
    private LocalDateTime createdOn;

    private String modifiedBy;
    private LocalDateTime modifiedOn;

    // Inverse side
    @OneToOne(mappedBy = "product",
              cascade = CascadeType.ALL,
              orphanRemoval = true,
              fetch = FetchType.EAGER)
    private Item item;

    // ===== Getters & Setters =====

    public Long getId() { return id; }

    public String getProductName() { return productName; }

    public void setProductName(String productName) { this.productName = productName; }

    public String getCreatedBy() { return createdBy; }

    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }

    public LocalDateTime getCreatedOn() { return createdOn; }

    public void setCreatedOn(LocalDateTime createdOn) { this.createdOn = createdOn; }

    public String getModifiedBy() { return modifiedBy; }

    public void setModifiedBy(String modifiedBy) { this.modifiedBy = modifiedBy; }

    public LocalDateTime getModifiedOn() { return modifiedOn; }

    public void setModifiedOn(LocalDateTime modifiedOn) { this.modifiedOn = modifiedOn; }

    public Item getItem() { return item; }

    public void setItem(Item item) {
        this.item = item;
        if (item != null) {
            item.setProduct(this); // maintain bidirectional sync
        }
    }
}