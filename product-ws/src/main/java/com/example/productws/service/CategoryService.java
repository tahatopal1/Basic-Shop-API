package com.example.productws.service;

import com.example.productws.dto.CategoryDTO;

import java.util.List;

public interface CategoryService {

    List<CategoryDTO> getAllCategories();

    CategoryDTO getCategory(String code);

    void createCategory(CategoryDTO categoryDTO);

    void updateCategory(String code, CategoryDTO categoryDTO);

    void deleteCategory(String code);

    void batchProcess();

}
