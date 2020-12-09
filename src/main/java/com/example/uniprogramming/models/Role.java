package com.example.uniprogramming.models;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    private String name;


    public Role(){};
    public Role(String name){
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getNameWithoutPrefix(){
        String _name  = name.substring(5);
        _name = _name.substring(0,1) + _name.substring(1).toLowerCase();
        return _name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String toString(){
        return this.name;
    }
}
