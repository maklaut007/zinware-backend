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
     * Update quantity of item in cart
     * @param itemId id of item to update quantity of
     * @param cartItem object with new quantity
     * @return Cart item that was updated and status 200
     */
    @PutMapping(path = "/{itemId}/")
    public ResponseEntity<CartItem> updateItemQuantity(@PathVariable Long itemId, @RequestBody CartItemRequest cartItem) {
        return cartService.updateItemQuantity(itemId, cartItem);
    }

    /**
     * Delete item from cart by id of item
     * @param itemId id of item to delete from cart
     * @return cart with item deleted
     */
    @DeleteMapping
    public Cart deleteItemFromCart(@PathVariable Long itemId) {
        return cartService.deleteItemFromCart(itemId);
    }
}
