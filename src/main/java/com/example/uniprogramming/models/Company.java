package com.example.uniprogramming.models;

import javax.persistence.*;
import java.sql.Date;

@Entity
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    private String name;
    private String nip;
    private String address;
    private String city;
    private Date added;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(
            name = "company_industry",
            joinColumns = @JoinColumn(
                    name = "company_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "industry_id", referencedColumnName = "id"))
    private Industry industry;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(
            name = "company_user",
            joinColumns = @JoinColumn(
                    name = "company_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"))
    private User user;

    private boolean isDeleted;

    public Company() {
    }


    public Company(String name, String nip, String address, String city, User user, Industry industry, Date added) {
        this.name = name;
        this.nip = nip;
        this.address = address;
        this.city = city;
        this.added = added;
        this.industry = industry;
        this.user = user;
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

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Date getAdded() {
        return added;
    }

    public void setAdded(Date added) {
        this.added = added;
    }

    public Industry getIndustry() {
        return industry;
    }

    public void setIndustry(Industry industry) {
        this.industry = industry;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean getIsDeleted(){
        return this.isDeleted;
    }

    public void setDeleted(boolean b) { this.isDeleted = b;
    }
}
