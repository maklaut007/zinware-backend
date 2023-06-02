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
     * Returns a category by id
     * @param categoryId  id of the category
     * @return category object
     * @throws InformationNotFoundException  if category is not found
     */
    public Category getCategory(Long categoryId) {
        Optional<Category> category = categoryRepository.findById(categoryId);
        if (category.isEmpty()) {
            throw new InformationNotFoundException("No category found with id" + categoryId);
        }
        return category.get();
    };

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

    /**
     * Returns a product in a category
     * @param categoryId  id of the category
     * @param productId  id of the product
     * @return product object
     * @throws InformationNotFoundException  if product is not found
     */
    public Product getProduct(Long categoryId, Long productId) {
        Optional<Product> product = productRepository.findByCategoryIdAndId(categoryId, productId);
        if (product.isEmpty()) {
            throw new InformationNotFoundException("No product found with id" + productId);
        }
        return product.get();
    }

    /**
     * Returns product by id
     * @param productId  id of the product
     * @return product object
     * @throws InformationNotFoundException  if product is not found
     */
    public Product getProduct(Long productId) {
        Optional<Product> product = productRepository.findById(productId);
        if (product.isEmpty()) {
            throw new InformationNotFoundException("No product found with id" + productId);
        }
        return product.get();
    }
}
