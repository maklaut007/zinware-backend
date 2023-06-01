package com.example.zinware.seed;

import com.example.zinware.model.Category;
import com.example.zinware.model.Product;
import com.example.zinware.repository.CategoryRepository;
import com.example.zinware.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    CategoryRepository categoryRepository;
    ProductRepository productRepository;

    @Autowired
    public void setCategoryRepository(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Autowired
    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * Runs on application startup
     *
     * @param args command line arguments
     * @throws Exception if something goes wrong
     */
    @Override
    public void run(String... args) throws Exception {
        loadUserData();
    }

    /**
     * Loads user data into database
     */
    private void loadUserData() {
        // Seed categories
        Category category1 = new Category("Mouses", "PC Mouses", "");
        Category category2 = new Category("Keyboards","PC Keyboards", "");
        Category category3 = new Category("Headphones","PC Headphones", "");

        categoryRepository.save(category1);
        categoryRepository.save(category2);
        categoryRepository.save(category3);

        // Seed products
        Product product1 = new Product("Logitech Mouse", "A mouse made by Logitech", 200.0, "https://images-na.ssl-images-amazon.com/images/I/61rk-0XJhPL._SL1500_.jpg", category1);
        Product product2 = new Product("Logitech Keyboard", "A keyboard made by Logitech", 200.0, "https://images-na.ssl-images-amazon.com/images/I/61rk-0XJhPL._SL1500_.jpg", category2);
        Product product3 = new Product("Logitech Headphones", "A headphones made by Logitech", 200.0, "https://images-na.ssl-images-amazon.com/images/I/61rk-0XJhPL._SL1500_.jpg", category3);
        Product product4 = new Product("Razer Mouse", "A mouse made by Razer", 200.0, "https://images-na.ssl-images-amazon.com/images/I/61rk-0XJhPL._SL1500_.jpg", category1);
        Product product5 = new Product("Razer Keyboard", "A keyboard made by Razer", 200.0, "https://images-na.ssl-images-amazon.com/images/I/61rk-0XJhPL._SL1500_.jpg", category2);
        Product product6 = new Product("Razer Headphones", "A headphones made by Razer", 200.0, "https://images-na.ssl-images-amazon.com/images/I/61rk-0XJhPL._SL1500_.jpg", category3);

        productRepository.save(product1);
        productRepository.save(product2);
        productRepository.save(product3);
        productRepository.save(product4);
        productRepository.save(product5);
        productRepository.save(product6);

        System.out.println("Data Loaded");
    }
}
