package com.example.uniprogramming.repositories;

import com.example.uniprogramming.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolesRepository extends JpaRepository<Role,Long> {
    Role findByName (String name);
}
