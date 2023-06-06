package com.example.zinware.controller;

import com.example.zinware.model.Checkout;
import com.example.zinware.service.CheckoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/checkout/")
public class CheckoutController {
    private CheckoutService checkoutService;

    @Autowired
    public void setCheckoutService(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }

    @PostMapping("/")
    public Checkout createCheckout(@RequestBody Checkout checkout) {
        return checkoutService.createCheckout(checkout);
    }

}
