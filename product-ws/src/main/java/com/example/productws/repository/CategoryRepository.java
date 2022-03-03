package com.example.productws.repository;

import com.example.productws.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, String> {
    Category findByCode(String code);
}
