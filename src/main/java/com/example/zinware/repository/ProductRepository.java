package com.example.zinware.repository;

import com.example.zinware.model.Category;
import com.example.zinware.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
