package com.example.zinware.service;

import com.example.zinware.model.*;
import com.example.zinware.model.cart.Cart;
import com.example.zinware.model.cart.CartItem;
import com.example.zinware.model.cart.CartItemRequest;
import com.example.zinware.model.login.User;
import com.example.zinware.repository.CartItemRepository;
import com.example.zinware.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartService {
    private CartRepository cartRepository;
    private CartItemRepository cartItemRepository;
    private CategoryService categoryService;

    @Autowired
    public void setCartRepository(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @Autowired
    public void setCartItemRepository(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }
    @Autowired
    public void setProductService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    public void removeItemFromCart() {
        System.out.println("removeItemFromCart");
    }

    public void updateItemQuantity() {
        System.out.println("updateItemQuantity");
    }

    /**
     * Get current logged in user's cart or create a new one if not exists
     *
     * @return cart object
     */
    public Cart getCart() {
        User user = UserService.getCurrentLoggedInUser();
        Optional<Cart> cart = cartRepository.findByUserId(user.getId());
        // Create cart if not exists
        if (!cart.isPresent()) {
            Cart newCart = new Cart();
            newCart.setUser(user);
            cartRepository.save(newCart);
            return newCart;
        }
        return cart.get();
    }

    public ResponseEntity<CartItem> addItemToCart(CartItemRequest cartItemRequest) {
        //TODO: Check if product is already in cart

        //Get cart and product
        Cart cart = getCart();
        Product product = categoryService.getProduct(cartItemRequest.getProductId());


        // Create cart item and save it to cart item repository
        CartItem cartItem = new CartItem(cart, product, cartItemRequest.getQuantity());
        cartItemRepository.save(cartItem);
        return ResponseEntity.status(HttpStatus.CREATED).body(cartItem);
    }
}


