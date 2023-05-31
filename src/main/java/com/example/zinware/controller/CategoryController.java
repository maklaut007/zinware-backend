package com.example.zinware.controller;

import com.example.zinware.model.Category;
import com.example.zinware.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/")
public class CategoryController {

    private CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /**
     * Get all categories
     * @return list of categories
     */
    @GetMapping(path = "/categories")
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }
}
