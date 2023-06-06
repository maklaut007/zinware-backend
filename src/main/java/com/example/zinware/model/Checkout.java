package com.example.zinware.model;

import javax.persistence.*;
import com.example.zinware.model.login.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "checkouts")
public class Checkout {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @Column
    private String address;

    @Column
    private String cardNumber;

    @Column
    private String cardHolder;

    @Column
    private String cardExpiry;

    @Column
    private String cardCvc;

    public Checkout(User user, String address, String cardNumber, String cardHolder, String cardExpiry, String cardCvc) {
        this.user = user;
        this.address = address;
        this.cardNumber = cardNumber;
        this.cardHolder = cardHolder;
        this.cardExpiry = cardExpiry;
        this.cardCvc = cardCvc;
    }

    public Checkout() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public void setCardHolder(String cardHolder) {
        this.cardHolder = cardHolder;
    }

    public String getCardExpiry() {
        return cardExpiry;
    }

    public void setCardExpiry(String cardExpiry) {
        this.cardExpiry = cardExpiry;
    }

    public String getCardCvc() {
        return cardCvc;
    }

    public void setCardCvc(String cardCvc) {
        this.cardCvc = cardCvc;
    }
}
