package com.example.zinware.service;

import com.example.zinware.model.Category;
import com.example.zinware.model.Product;
import com.example.zinware.repository.CategoryRepository;
import com.example.zinware.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    private CategoryRepository categoryRepository;
    private ProductRepository productRepository;

    @Autowired
    public void setCategoryRepository(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Autowired
    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * Returns all categories
     * @return list of categories
     */
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    /**
     * Returns all products in a category
     * @param categoryId  id of the category
     * @return list of products
     */
    public List<Product> getCategoryProducts(Long categoryId) {
        Optional<Category> category = categoryRepository.findById(categoryId);
        System.out.println(category.get().getName());
        if(category.isEmpty()) {
            //Change later to throw custom exception
            throw new RuntimeException("Category not found for id" + categoryId);
        }

        Optional<List<Product>> products = productRepository.findByCategory(category.get());
        System.out.println(products);
        return products.get();
    }
}
