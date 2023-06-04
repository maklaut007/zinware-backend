package com.example.zinware.controller;

import com.example.zinware.model.cart.Cart;
import com.example.zinware.model.cart.CartItem;
import com.example.zinware.model.cart.CartItemRequest;
import com.example.zinware.service.CartService;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<CartItem> addItemToCart(@RequestBody CartItemRequest cartItemRequest) {
        return cartService.addItemToCart(cartItemRequest);
    }

    /**
     * Decrease quantity of item in cart by 1
     * @param itemId id of item to decrease quantity of
     * @return new quantity of item in cart
     */
    @PutMapping(path = "/{itemId}/decrease-quantity/")
    public ResponseEntity<Integer> decreaseItemQuantity(@PathVariable("itemId") Long itemId) {
        return cartService.decreaseItemQuantity(itemId);
    }
    /**
     * Increase quantity of item in cart by 1
     * @param itemId id of item to increase quantity of
     * @return new quantity of item in cart
     */
    @PutMapping(path = "/{itemId}/increase-quantity/")
    public ResponseEntity<Integer> increaseItemQuantity(@PathVariable("itemId") Long itemId) {
        return cartService.increaseItemQuantity(itemId);
    }

    /**
     * Update quantity of item in cart
     * @param itemId id of item to update quantity of
     * @param quantity  new quantity of item
     * @return Cart item that was updated and status 200
     */
    @PutMapping(path = "/{itemId}/")
    public ResponseEntity<CartItem> updateItemQuantity(@PathVariable Long itemId, @RequestBody Integer quantity) {
        return cartService.updateItemQuantity(itemId, quantity);
    }
}
