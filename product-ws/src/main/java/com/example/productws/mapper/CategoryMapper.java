package com.example.productws.mapper;

import com.example.productws.dto.CategoryDTO;
import com.example.productws.model.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper implements ModelMapper<Category, CategoryDTO> {

    @Override
    public CategoryDTO straightMapping(Category source, CategoryDTO destination) {
        return null;
    }

    @Override
    public Category reverseMapping(Category source, CategoryDTO destination) {
        return null;
    }

}
