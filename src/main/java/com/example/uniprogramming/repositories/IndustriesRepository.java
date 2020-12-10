package com.example.uniprogramming.repositories;

import com.example.uniprogramming.models.Industry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IndustriesRepository extends JpaRepository<Industry,Long> {
    Industry findByName (String name);
    List<Industry> findAll();
}
