package com.project.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    private String id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 50)
    private String surname;

    @Column(nullable = false)
    private Date birthDate;

    @Column(nullable = false, length = 120, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 400)
    private String address;

    @Column(nullable = false)
    private String phone;

}
