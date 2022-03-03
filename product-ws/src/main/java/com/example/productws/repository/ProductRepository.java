package com.example.productws.repository;

import com.example.productws.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, String> {
    Product findByCode(String code);
}
