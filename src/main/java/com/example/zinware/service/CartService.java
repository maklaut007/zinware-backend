package com.example.zinware.service;

import com.example.zinware.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {
    private CartRepository cartRepository;
    @Autowired
    public void setCartRepository(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public void addItemToCart() {
        System.out.println("addItemToCart");
    }

    public void removeItemFromCart() {
        System.out.println("removeItemFromCart");
    }

    public void updateItemQuantity() {
        System.out.println("updateItemQuantity");
    }

    public void viewCart() {
        System.out.println("viewCart");
    }

}
