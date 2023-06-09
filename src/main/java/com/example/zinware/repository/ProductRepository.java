package com.example.zinware.repository;

import com.example.zinware.model.Category;
import com.example.zinware.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<List<Product>> findByCategoryId(Long categoryId);
    Optional<Product> findByCategoryIdAndId(Long categoryId, Long id);

}
