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
    private String name;

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
}
