package com.example.productws.service;

import com.example.productws.dto.ProductDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{
    @Override
    public List<ProductDTO> getAllProducts() {
        return null;
    }

    @Override
    public ProductDTO getProduct(String email) {
        return null;
    }

    @Override
    public void createProduct(ProductDTO productDTO) {

    }

    @Override
    public void updateProduct(String email, ProductDTO productDTO) {

    }

    @Override
    public void deleteProduct(String email) {

    }

    @Override
    public void batchProcess() throws JsonProcessingException {

    }
}
