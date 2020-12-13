package com.example.uniprogramming.models;

import javax.persistence.*;

@Entity
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    long id;

    private String name;
    private String surname;
    private String phone;
    private String email;
    private String position;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(
            name = "contact_company",
            joinColumns = @JoinColumn(
                    name = "contact_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "company_id", referencedColumnName = "id"))
    private Company company;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(
            name = "contact_user",
            joinColumns = @JoinColumn(
                    name = "contact_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"))
    private User user;

    private boolean isDeleted;

    public Contact(){}

    public Contact(String name, String surname, String phone, String email, String position){
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.email = email;
        this.position = position;
        this.isDeleted = false;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }
}
