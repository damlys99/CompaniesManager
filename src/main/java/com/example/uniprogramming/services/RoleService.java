package com.example.uniprogramming.services;

import com.example.uniprogramming.models.Role;
import com.example.uniprogramming.repositories.RolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    private final RolesRepository rolesRepository;

    @Autowired
    public RoleService(RolesRepository rolesRepository){
        this.rolesRepository = rolesRepository;
    }

    public Role getRole(String name){
        return rolesRepository.findByName(name);
    }
}
