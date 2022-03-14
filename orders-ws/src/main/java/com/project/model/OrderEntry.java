package com.project.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "order_entries")
@Data
public class OrderEntry {

    @Id
    private String id;

    private String name;
    private String code;
    private int count;
    private double price;

}
