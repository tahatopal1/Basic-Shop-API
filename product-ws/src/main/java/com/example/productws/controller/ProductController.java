package com.example.productws.controller;

import com.example.productws.dto.ProductDTO;
import com.example.productws.service.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/v1/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private Environment env;

    @GetMapping("/status/check")
    public String status(){
        return "Working on port " + env.getProperty("local.server.port")
                + " with config environment = " + env.getProperty("token.check")
                + "\n" + "Test value: " + env.getProperty("token.test");
    }

    @GetMapping
    public List<ProductDTO> getAllProducts(){
        return productService.getAllProducts();
    }

    @GetMapping("/suggestion")
    public List<ProductDTO> getRandomSuggestions(){
        List<ProductDTO> allProducts = productService.getAllProducts();
        List<ProductDTO> suggestions = new ArrayList<>();
        for (int i = 0; i < 3; i++){
            ProductDTO productDTO = allProducts.get(new Random().nextInt(allProducts.size()) + 1);
            suggestions.add(productDTO);
        }
        return suggestions;
    }


    @GetMapping("/product")
    public ProductDTO getProduct(@RequestParam String code){
        return productService.getProduct(code);
    }

    @PostMapping
    public ProductDTO createProduct(@Valid @RequestBody ProductDTO productDTO){
        productService.createProduct(productDTO);
        return productDTO;
    }

    @PostMapping("/batch")
    public String getRoot() throws JsonProcessingException {
        productService.batchProcess();
        return "Batch has finished successfully!";
    }

    @PutMapping("/exchange")
    public ResponseEntity exchange(@RequestParam String code, @RequestParam Integer count){
        if (productService.decreaseStock(code, count))
            return ResponseEntity.status(HttpStatus.OK).body(null);
        else
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    @PutMapping
    public ProductDTO updateProduct(@RequestParam String code, @Valid @RequestBody ProductDTO productDTO){
        productService.updateProduct(code, productDTO);
        return productDTO;
    }

    @DeleteMapping
    public String deleteProduct(@RequestParam String code){
        productService.deleteProduct(code);
        return "Product has been successfully deleted!";
    }

}
