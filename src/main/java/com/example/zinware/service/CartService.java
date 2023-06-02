package com.example.zinware.service;

import com.example.zinware.model.Cart;
import com.example.zinware.model.User;
import com.example.zinware.repository.CartRepository;
import com.example.zinware.security.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    /**
     * Get current logged in user's cart or create a new one if not exists
     * @return cart object
     */
    public Cart getCart() {
        User user = UserService.getCurrentLoggedInUser();
        Optional<Cart> cart = cartRepository.findByUserId(user.getId());
        // Create cart if not exists
        if(!cart.isPresent()) {
            Cart newCart = new Cart();
            newCart.setUser(user);
            cartRepository.save(newCart);
            return newCart;
        }
        return cart.get();

    }

}
