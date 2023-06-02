package com.example.zinware.controller;

import com.example.zinware.model.Cart;
import com.example.zinware.model.CartItem;
import com.example.zinware.model.Product;
import com.example.zinware.service.CartService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    /**
     * Get cart with all items in it
     * @return cart
     */
    @GetMapping(path = "/")
    public Cart getCart() {
        return cartService.getCart();
    };

    /**
     * Add item to cart
     * @return cart item that was added
     */
    @PostMapping(path = "/")
    public CartItem addItemToCart(@RequestBody Long productId, @RequestBody Integer quantity) {
        return cartService.addItemToCart(productId, quantity);
    }
}
