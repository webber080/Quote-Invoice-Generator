package com.example.tcstest.db_stuff.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data // Sets the getters & setters
@Table(name = "USERS") // Don't really need to put (name = "USERS"), as the class name itself can be used as the table name
public class Users{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "FName")
    private String firstName;

    @Column(name = "LName")
    private String lastName;

    @Column(name = "Email")
    private String email;

    @Column(name = "Password")
    private String password;

}
