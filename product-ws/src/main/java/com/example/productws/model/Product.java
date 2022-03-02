package com.example.productws.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "products")
@Data
public class Product {

    @Id
    private String id;

    @Column(nullable = false, length = 75)
    private String name;

    @Column(nullable = false, length = 75, unique = true)
    private String code;

    @Column(nullable = false)
    private int stock;

    @Column(nullable = false)
    private double price;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "category_id")
    private Category category;

}
