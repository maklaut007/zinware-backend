package com.example.zinware.repository;

import com.example.zinware.model.cart.Cart;
import com.example.zinware.model.cart.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface CartItemRepository extends JpaRepository<CartItem,  Long> {
    Optional<CartItem> findByProductIdAndCartId(Long productId, Long cartId);
    Optional<List<CartItem>> findAllByCart(Cart cart);
}
