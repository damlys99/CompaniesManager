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

    private String userName;
    private String password;
    private String roles;

    private boolean isDeleted;

    public User(String name, String surname, Date dateOfBirth, String userName, String password, String roles){
        this.name = name;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;
        this.userName = userName;
        this.password = password;
        this.roles = roles;
        this.isDeleted = false;
    }

    public User(){}

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

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getRoles() {
        return roles;
    }

    public boolean getIsDeleted(){
        return this.isDeleted;
    }

    public void delete(){
        this.isDeleted = true;
    }

}
