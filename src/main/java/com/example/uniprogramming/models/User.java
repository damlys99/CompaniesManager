package com.example.uniprogramming.models;

import org.hibernate.engine.internal.Cascade;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.sql.Date;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

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

    @ManyToMany(fetch = FetchType.EAGER, cascade=CascadeType.MERGE)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles = new HashSet<>();


    private boolean isDeleted;

    public User(String name, String surname, Date dateOfBirth, String userName, String password){
        this.name = name;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;
        this.userName = userName;
        this.password = password;
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

    public Set<Role> getRoles() {
        return roles;
    }

    public boolean getIsDeleted(){
        return this.isDeleted;
    }
    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void addToRoles(Role role){
        this.roles.add(role);
    }
    public void delete(){
        this.isDeleted = true;
    }

}
