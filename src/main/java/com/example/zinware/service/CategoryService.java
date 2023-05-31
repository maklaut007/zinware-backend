package com.example.zinware.service;

import com.example.zinware.exception.InformationNotFoundException;
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
        Optional<List<Product>> products = productRepository.findByCategoryId(categoryId);
        if (products.isEmpty()) {
            throw new InformationNotFoundException("No products found in this category" + categoryId);
        }
        return products.get();
    }
}
