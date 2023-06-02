package com.example.zinware.model.cart;

import com.example.zinware.model.Product;
import com.example.zinware.model.cart.Cart;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
@Entity
@Table(name = "cart_item")
public class CartItem {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart;

    public CartItem(Cart cart, Product product,  int quantity) {
        this.quantity = quantity;
        this.product = product;
        this.cart = cart;
    }

    public CartItem() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }
}
