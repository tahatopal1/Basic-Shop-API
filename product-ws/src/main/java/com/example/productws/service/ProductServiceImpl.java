package com.example.productws.service;

import com.example.productws.dto.CategoryDTO;
import com.example.productws.dto.ProductDTO;
import com.example.productws.mapper.ProductMapper;
import com.example.productws.model.Category;
import com.example.productws.model.Product;
import com.example.productws.repository.CategoryRepository;
import com.example.productws.repository.ProductRepository;
import com.example.productws.util.FileUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private Environment environment;

    @Autowired
    private FileUtil fileUtil;

    @Override
    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(productMapper::straightMapping)
                .collect(Collectors.toList());
    }

    @Override
    public ProductDTO getProduct(String code) {
        Product product = Optional.ofNullable(productRepository.findByCode(code)).orElseThrow(() -> new RuntimeException("Product is not found"));
        return productMapper.straightMapping(product);
    }

    @Override
    public void createProduct(ProductDTO productDTO) {
        Product product = productMapper.reverseMapping(productDTO);
        product.setId(UUID.randomUUID().toString());
        product.getCategory().getProduct().add(product);
        categoryRepository.save(product.getCategory());
    }

    @Override
    public void updateProduct(String code, ProductDTO productDTO) {
        Product product = Optional.ofNullable(productRepository.findByCode(code)).orElseThrow(() -> new RuntimeException("Product is not found"));
        mergeDTOInfo(productDTO, product);
        productRepository.save(product);
    }

    private void mergeDTOInfo(ProductDTO productDTO, Product product) {
        product.setName(productDTO.getName());
        product.setCode(productDTO.getCode());
        product.setPrice(productDTO.getPrice());
        product.setStock(productDTO.getStock());

        Category category =
                Optional.ofNullable(categoryRepository.findByCode(productDTO.getCategoryDTO().getCode())).orElseThrow(() -> new RuntimeException("Category is not found"));
        product.setCategory(category);
    }

    @Override
    public void deleteProduct(String code) {
        Product product = Optional.ofNullable(productRepository.findByCode(code)).orElseThrow(() -> new RuntimeException("Product is not found"));
        productRepository.delete(product);
    }

    @Override
    public void batchProcess() {
        String url = environment.getProperty("spring.products.filepath");
        StringBuffer stringBuffer = fileUtil.readFile(url);
        Arrays.stream(stringBuffer.toString().split("\n")).forEach(line -> {
            String[] info = line.split("-");
            try {
                this.createProduct(new ProductDTO(info[0],
                                    info[1],
                                    Integer.parseInt(info[2]),
                                    Double.parseDouble(info[3]),
                                    new CategoryDTO("", info[4], null)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public boolean decreaseStock(String code, int count) {
        Product product = productRepository.findByCode(code);
        if (product.getStock() > count){
            product.setStock(product.getStock() - count);
            productRepository.save(product);
            return true;
        }else
            return false;
    }
}
