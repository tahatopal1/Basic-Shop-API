package com.example.productws.service;

import com.example.productws.dto.ProductDTO;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface ProductService {

    List<ProductDTO> getAllProducts();

    ProductDTO getProduct(String code);

    void createProduct(ProductDTO productDTO);

    void updateProduct(String code, ProductDTO productDTO);

    void deleteProduct(String code);

    void batchProcess() throws JsonProcessingException;

    boolean decreaseStock(String code, int count);

}
