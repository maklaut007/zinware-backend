package com.example.zinware.service;

import com.example.zinware.model.*;
import com.example.zinware.repository.CartItemRepository;
import com.example.zinware.repository.CartRepository;
import com.example.zinware.security.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

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
        categoryService = categoryService;
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

    public CartItem addItemToCart(Long productId, Integer quantity) {
        //TODO: Check if product is already in cart

        //Get cart and product
        Cart cart = getCart();
        Product product = categoryService.getProduct(productId);

        // Create cart item and save it to cart item repository
        CartItem cartItem = new CartItem(cart, product, quantity);
        cartItemRepository.save(cartItem);
        return cartItem;
    }
}


