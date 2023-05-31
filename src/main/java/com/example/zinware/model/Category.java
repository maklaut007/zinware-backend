package com.example.zinware.model;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;


@Entity
@Table(name = "agents")
public class Category {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String picture;
}
