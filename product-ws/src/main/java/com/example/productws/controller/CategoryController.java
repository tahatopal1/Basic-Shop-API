package com.example.productws.controller;

import com.example.productws.dto.CategoryDTO;
import com.example.productws.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/category")
    public CategoryDTO getCategory(@RequestParam String code){
        return categoryService.getCategory(code);
    }

    @GetMapping
    public List<CategoryDTO> getAllCategories(){
        return categoryService.getAllCategories();
    }

    @PostMapping
    public CategoryDTO createCategory(@Valid @RequestBody CategoryDTO categoryDTO){
        categoryService.createCategory(categoryDTO);
        return categoryDTO;
    }

    @PutMapping
    public CategoryDTO updateCategory(@RequestParam String code, @Valid @RequestBody CategoryDTO categoryDTO){
        categoryService.updateCategory(code, categoryDTO);
        return categoryDTO;
    }

    @DeleteMapping
    public String deleteCategory(@RequestParam String code){
        categoryService.deleteCategory(code);
        return "Category has been successfully deleted!";
    }

    @PostMapping("/batch")
    public String getRoot()  {
        categoryService.batchProcess();
        return "Batch has finished successfully!";
    }

}
