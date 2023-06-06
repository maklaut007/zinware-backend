package com.example.zinware.service;

import com.example.zinware.model.Checkout;
import com.example.zinware.repository.CheckoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CheckoutService {
    private CheckoutRepository checkoutRepository;

    @Autowired
    public void setCheckoutRepository(CheckoutRepository checkoutRepository) {
        this.checkoutRepository = checkoutRepository;
    }
    public Checkout createCheckout(Checkout checkout) {
        if(UserService.getCurrentLoggedInUser()!=null){
            checkout.setUser(UserService.getCurrentLoggedInUser());
            return checkoutRepository.save(checkout);
        }
        return null;
    }
}
