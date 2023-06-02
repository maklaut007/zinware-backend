package com.example.zinware.controller;

import com.example.zinware.model.Cart;
import com.example.zinware.service.CartService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }
    @GetMapping
    public Cart getCart() {
        return cartService.getCart();
    };
}
