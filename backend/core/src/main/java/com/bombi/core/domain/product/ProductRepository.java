package com.bombi.core.domain.product;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bombi.core.domain.product.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
