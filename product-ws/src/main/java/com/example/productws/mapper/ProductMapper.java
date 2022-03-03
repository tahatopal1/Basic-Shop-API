package com.example.productws.mapper;

import com.example.productws.dto.ProductDTO;
import com.example.productws.model.Category;
import com.example.productws.model.Product;
import com.example.productws.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ProductMapper implements ModelMapper<Product, ProductDTO> {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public ProductDTO straightMapping(Product product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setCode(product.getCode());
        productDTO.setName(product.getName());
        productDTO.setPrice(product.getPrice());
        productDTO.setStock(product.getStock());
        productDTO.setCategoryDTO(categoryMapper.straightMapping(product.getCategory()));
        return productDTO;
    }

    @Override
    public Product reverseMapping(ProductDTO productDTO) {
        Product product = new Product();
        product.setCode(productDTO.getCode());
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setStock(productDTO.getStock());

        Category category =
                Optional.ofNullable(categoryRepository.findByCode(productDTO.getCategoryDTO().getCode())).orElseThrow(() -> new RuntimeException());
        product.setCategory(category);
        return product;
    }
}
