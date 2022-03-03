package com.example.productws.service;

import com.example.productws.dto.CategoryDTO;
import com.example.productws.mapper.CategoryMapper;
import com.example.productws.model.Category;
import com.example.productws.repository.CategoryRepository;
import com.example.productws.util.FileUtil;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private FileUtil fileUtil;

    @Autowired
    private Environment environment;

    @Override
    public void createCategory(CategoryDTO categoryDTO) {
        Category category = categoryMapper.reverseMapping(categoryDTO);
        category.setId(UUID.randomUUID().toString());
        categoryRepository.save(category);
    }

    @Override
    public void updateCategory(String code, CategoryDTO categoryDTO) {
        Category category = Optional.ofNullable(categoryRepository.findByCode(code)).orElseThrow(() -> new RuntimeException("Category is not found"));
        mergeDTOInfo(categoryDTO, category);
        categoryRepository.save(category);
    }

    private void mergeDTOInfo(CategoryDTO categoryDTO, Category category) {
        category.setName(categoryDTO.getName());
        category.setCode(categoryDTO.getCode());
        if (Strings.isNotEmpty(categoryDTO.getParentCode())){
            Category cat = categoryRepository.findByCode(categoryDTO.getParentCode());
            category.setParentCategory(cat);
        }
    }

    @Override
    public void deleteCategory(String code) {
        Category category = Optional.ofNullable(categoryRepository.findByCode(code)).orElseThrow(() -> new RuntimeException("Category is not found"));
        categoryRepository.delete(category);
    }

    @Override
    public List<CategoryDTO> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::straightMapping)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDTO getCategory(String code) {
        Category category = categoryRepository.findByCode(code);
        return categoryMapper.straightMapping(category);
    }

    @Override
    public void batchProcess() {
        String url = environment.getProperty("spring.categories.filepath");
        StringBuffer stringBuffer = fileUtil.readFile(url);
        Arrays.stream(stringBuffer.toString().split("\n")).forEach(line -> {
            String[] info = line.split("-");
            this.createCategory(new CategoryDTO(info[0], info[1], (info.length == 3) ? info[2] : null));
        });

    }
}
