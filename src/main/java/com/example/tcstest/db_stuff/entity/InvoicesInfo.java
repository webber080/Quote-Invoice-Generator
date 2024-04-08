package com.example.tcstest.db_stuff.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Data // Sets the getters & setters
@Table(name = "InvoicesInfo")
public class InvoicesInfo{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "invoiceID")
    private Integer invoiceID;

    // COMPANY
    @Column(name = "cName")
    private String cName;

    @Column(name = "cStreetAddress")
    private String cStreetAddress;

    @Column(name = "cCityProvince")
    private String cCityProvince;

    @Column(name = "cPostalCode")
    private String cPostalCode;

    @Column(name = "cPhone")
    private String cPhone;

    @Column(name = "cEmail")
    private String cEmail;


    // USER/CUSTOMER
    @Column(name = "uName")
    private String uName;

    @Column(name = "uStreetAddress")
    private String uStreetAddress;

    @Column(name = "uCityProvince")
    private String uCityProvince;

    @Column(name = "uPostalCode")
    private String uPostalCode;

    @Column(name = "uPhone")
    private String uPhone;

    @Column(name = "uEmail")
    private String uEmail;


    // CREATED-AT DATE
    @Column(name = "createdAt")
    private String createdAt;


    // Getter and setter for userInfo
    // Define the foreign key referencing the Users entity
    // Many instances of the current entity (InvoicesInfo) can be associated with one instance of the related entity (Users)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id") // Name of the foreign key column in the InvoicesInfo table
    private Users usersInfo; // Reference to the UsersInfo entity

}

