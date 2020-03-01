package com.mystore.demo.model;

import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
public class Customer {

    @Id
    private Integer id;

    private String firstname;

    private String lastname;

    private String country;


    public Customer(){}

    public Customer(Integer id, String firstname, String lastname, String country) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.country = country;
    }

    public Integer getId() {
        return id;
    }


    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "{" +
                "id:" + id +
                ", firstname:'" + firstname + '\'' +
                ", lastname:'" + lastname + '\'' +
                ", country:'" + country + '\'' +
                '}';
    }
}
