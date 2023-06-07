package com.example.zinware.service;

import com.example.zinware.model.Checkout;
import com.example.zinware.repository.CheckoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CheckoutService {
    private CheckoutRepository checkoutRepository;
    private CartService cartService;

    @Autowired
    public void setCheckoutRepository(CheckoutRepository checkoutRepository) {
        this.checkoutRepository = checkoutRepository;
    }

    @Autowired
    public void setCartService(CartService cartService) {
        this.cartService = cartService;
    }

    /**
     * Saving checkout in database and deleting all items from cart otherwise return null
     * @param checkout object
     * @return checkout object that was saved in database
     */
    public Checkout createCheckout(Checkout checkout) {
        //checking if user is logged in
        if(UserService.getCurrentLoggedInUser()!=null){
            //setting user to checkout object and deleting all items from cart
            checkout.setUser(UserService.getCurrentLoggedInUser());
            cartService.deleteAllItemsFromCart();
            return checkoutRepository.save(checkout);
        }
        return null;
    }
}
