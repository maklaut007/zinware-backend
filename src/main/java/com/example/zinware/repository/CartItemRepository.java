package com.example.zinware.repository;

import com.example.zinware.model.cart.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CartItemRepository extends JpaRepository<CartItem,  Long> {
}
