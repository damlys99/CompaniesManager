package com.example.uniprogramming;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Date;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    private String name;
    private String surname;
    private Date dateOfBirth;

    private boolean isDeleted;

    public User(){}

    public User(String name, String surname, Date dateOfBirth){
        this.name = name;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;

        this.isDeleted = false;
    }

    public long getId(){
        return this.id;
    }
    public String getName(){
        return this.name;
    }

    public String getSurname(){
        return this.surname;
    }

    public Date getDateOfBirth(){
        return this.dateOfBirth;
    }

    public boolean isDeleted(){
        return this.isDeleted;
    }

}
