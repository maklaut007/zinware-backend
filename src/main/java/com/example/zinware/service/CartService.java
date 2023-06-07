package com.example.zinware.service;

import com.example.zinware.exception.InformationNotFoundException;
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
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
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

    /**
     * Get current logged-in user's cart or create a new one if not exists
     *
     * @return cart object
     */
    public Cart getCart() {
        User user = UserService.getCurrentLoggedInUser();
        Optional<Cart> cart = cartRepository.findByUserId(user.getId());
        // Create cart if not exists
        if (cart.isEmpty()) {
            Cart newCart = new Cart();
            newCart.setUser(user);
            cartRepository.save(newCart);
            return newCart;
        }
        return cart.get();
    }

    /**
     * Add item to cart or increase the quantity of an existing item in cart if it already exists
     *
     * @param cartItemRequest cart item request object that contains product id and quantity of the item
     * @return cart item object that is saved in cart item repository
     */
    public ResponseEntity<CartItem> addItemToCart(CartItemRequest cartItemRequest) {
        // Getting current logged-in user's cart
        Cart cart = getCart();
        // Getting item from cart item repository
        CartItem cartItem = cartItemRepository.findByProductIdAndCartId(cartItemRequest.getProductId(), cart.getId()).orElse(null);
        // If cart item already exists, increase the cart item's quantity

        if (cartItem != null) {
            cartItem.setQuantity(cartItem.getQuantity() + cartItemRequest.getQuantity());
            cartItemRepository.save(cartItem);
            return ResponseEntity.status(HttpStatus.CREATED).body(cartItem);
        }

        // If cart item does not exist in cart, create a new one
        Product product = categoryService.getProduct(cartItemRequest.getProductId());

        // Create cart item and save it to cart item repository
        cartItem = new CartItem(cart, product, cartItemRequest.getQuantity());
        cartItemRepository.save(cartItem);
        return ResponseEntity.status(HttpStatus.CREATED).body(cartItem);
    }

    /**
     * Update item quantity in cart by the given quantity for the given cart item id
     *
     * @param itemId cart item id to change quantity of
     * @param item   CartItemRequest object that contains new quantity
     * @return cart item object that was changed
     */
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<CartItem> updateItemQuantity(Long itemId, CartItemRequest item) {
        // Find item in cart by the given item id
        CartItem cartItem = cartItemRepository.findById(itemId).orElseThrow(
                () -> new InformationNotFoundException("Item not found in the cart")
        );

        // Check if user has the item in their cart
        if (!cartItem.getCart().getUser().getId().equals(UserService.getCurrentLoggedInUser().getId()) ) {
            throw new InformationNotFoundException("Item not found in user's cart");
        }

        cartItem.setQuantity(item.getQuantity());
        cartItemRepository.save(cartItem);
        return ResponseEntity.status(HttpStatus.OK).body(cartItem);
    }

    /**
     * Delete item from cart by the given item id
     *
     * @param itemId cart item id to delete
     * @return cart object that contains all items in the cart after deletion
     * @throws InformationNotFoundException if the item id is not found in the cart
     */
    public Cart deleteItemFromCart(Long itemId) {

        CartItem cartItem = cartItemRepository.findById(itemId).orElseThrow(
                () -> new InformationNotFoundException("Item not found in the cart")
        );

        // Check if user has the item in their cart
        boolean isInUserCart = cartItem.getCart().getUser().getId().equals(UserService.getCurrentLoggedInUser().getId()) ;
        if (!isInUserCart) {
            throw new InformationNotFoundException("Item not found in user's cart");
        }

        cartItemRepository.delete(cartItem);
        return getCart();
    }

    /**
     * Delete all items from cart
     * @return cart object that contains all items in the cart after deletion
     * @throws InformationNotFoundException if the cart is empty or user is not found
     */
    public Cart deleteAllItemsFromCart() {
        User loggedInUser = UserService.getCurrentLoggedInUser();
        Cart cart = cartRepository.findByUserId(loggedInUser.getId()).orElseThrow(() -> new InformationNotFoundException("No cart found for this user"));
        List<CartItem> userCartItems = cartItemRepository.findAllByCart(cart).get();
        if (userCartItems.isEmpty()) {
            throw new InformationNotFoundException("Cart is already empty");
        }
        cartItemRepository.deleteAll(userCartItems);
        return getCart();
    }
}


