package com.example.productws.mapper;

import com.example.productws.dto.CategoryDTO;
import com.example.productws.model.Category;
import com.example.productws.repository.CategoryRepository;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CategoryMapper implements ModelMapper<Category, CategoryDTO> {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public CategoryDTO straightMapping(Category category) {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setCode(category.getCode());
        categoryDTO.setName(category.getName());
        Optional.ofNullable(category.getParentCategory()).ifPresent(cat -> categoryDTO.setParentCode(cat.getCode()));
        return categoryDTO;
    }

    @Override
    public Category reverseMapping(CategoryDTO categoryDTO) {
        Category category = new Category();
        category.setCode(categoryDTO.getCode());
        category.setName(categoryDTO.getName());
        if (Strings.isNotEmpty(categoryDTO.getParentCode())){
            Category cat = categoryRepository.findByCode(categoryDTO.getParentCode());
            category.setParentCategory(cat);
        }
        return category;
    }
}
