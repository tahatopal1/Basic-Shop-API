package com.example.productws.service;

import com.example.productws.dto.ProductDTO;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface ProductService {

    List<ProductDTO> getAllProducts();

    ProductDTO getProduct(String email);

    void createProduct(ProductDTO productDTO);

    void updateProduct(String email, ProductDTO productDTO);

    void deleteProduct(String email);

    void batchProcess() throws JsonProcessingException;

}
