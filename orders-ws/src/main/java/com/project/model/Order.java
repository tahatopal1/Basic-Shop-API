package com.project.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
public class Order {

    @Id
    private String id;

    @Column(unique = true)
    private String code;

    private String username;

    @Column
    private Double totalCost;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id")
    private List<OrderEntry> orderEntries;

}
