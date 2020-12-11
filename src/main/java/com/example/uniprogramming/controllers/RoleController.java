package com.example.uniprogramming.controllers;

import com.example.uniprogramming.models.Role;
import com.example.uniprogramming.repositories.RolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    private final RolesRepository rolesRepository;

    @Autowired
    public RoleController(RolesRepository rolesRepository){
        this.rolesRepository = rolesRepository;
    }

    @RequestMapping("/all")
    public List<Role> getAll(){
        return rolesRepository.findAll();
    }
}
