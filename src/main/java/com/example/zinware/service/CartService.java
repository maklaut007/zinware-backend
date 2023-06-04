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
        System.out.println(" Cart ");
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

    /**
     * Add item to cart or increase the quantity of an existing item in cart if it already exists
     * @param cartItemRequest cart item request object that contains product id and quantity of the item
     * @return cart item object that is saved in cart item repository
     */
    public ResponseEntity<CartItem> addItemToCart(CartItemRequest cartItemRequest) {

        // If cart item already exists, increase the cart item's quantity
        Optional<CartItem> cartItemInRepo = cartItemRepository.findById(cartItemRequest.getProductId());
        if(cartItemInRepo.isPresent()){
            cartItemInRepo.get().setQuantity(cartItemInRepo.get().getQuantity() + cartItemRequest.getQuantity());
            return ResponseEntity.status(HttpStatus.CREATED).body(cartItemInRepo.get());
        }

        // If cart item does not exist in cart, create a new one
        //Get cart and product
        Cart cart = getCart();
        Product product = categoryService.getProduct(cartItemRequest.getProductId());

        // Create cart item and save it to cart item repository
        CartItem cartItem = new CartItem(cart, product, cartItemRequest.getQuantity());
        cartItemRepository.save(cartItem);
        return ResponseEntity.status(HttpStatus.CREATED).body(cartItem);
    }

    /**
     * Increase item quantity in cart by 1 for the given cart item id
     * @param cartItemId id of the cart item
     * @return quantity of the item after increase operation
     */
    public ResponseEntity<Integer> increaseItemQuantity(Long cartItemId) {
        // TODO : Check if cartItemId exists
        CartItem cartItem = cartItemRepository.findById(cartItemId).get();
        cartItem.setQuantity(cartItem.getQuantity() + 1);
        cartItemRepository.save(cartItem);
        return ResponseEntity.status(HttpStatus.OK).body(cartItem.getQuantity());
    }

    /**
     * Decrease item quantity in cart by 1 for the given cart item id
     * @param cartItemId id of the cart item
     * @return quantity of the item after increase operation
     */
    public ResponseEntity<Integer> decreaseItemQuantity(Long cartItemId) {
        // TODO : Check if cartItemId exists
        CartItem cartItem = cartItemRepository.findById(cartItemId).get();
        cartItem.setQuantity(cartItem.getQuantity() - 1);
        cartItemRepository.save(cartItem);
        return ResponseEntity.status(HttpStatus.OK).body(cartItem.getQuantity());
    }

}


