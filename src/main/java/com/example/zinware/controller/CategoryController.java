package com.example.zinware.controller;

import com.example.zinware.model.Category;
import com.example.zinware.model.Product;
import com.example.zinware.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/")
public class CategoryController {

    private CategoryService categoryService;

    @Autowired
    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /**
     * Get all categories
     * @return list of categories
     */
    @GetMapping(path = "/categories/") //http://localhost:8080/api/categories/
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    /**
     * Get all products in a category
     * @param categoryId id of the category
     * @return list of products
     */
    @GetMapping(path = "/categories/{categoryId}/products/") //http://localhost:8080/api/categories/1/products/
    public List<Product> getCategoryProducts(@PathVariable Long categoryId) {
        return categoryService.getCategoryProducts(categoryId);
    }

    /**
     * Get a product in a category by id
     * @param categoryId    id of the category
     * @param productId     id of the product
     * @return product object if not found return 404
     */
    @GetMapping(path = "/categories/{categoryId}/products/{productId}") //http://localhost:8080/api/categories/1/products/1
    public Product getProduct(@PathVariable Long categoryId, @PathVariable Long productId) {
        return categoryService.getProduct(categoryId, productId);
    }
}
