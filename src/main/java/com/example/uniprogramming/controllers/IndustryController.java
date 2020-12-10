package com.example.uniprogramming.controllers;


import com.example.uniprogramming.models.Industry;
import com.example.uniprogramming.repositories.IndustriesRepository;
import com.example.uniprogramming.services.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/industries")
public class IndustryController {

    private final IndustriesRepository industriesRepository;

    @Autowired
    public IndustryController(IndustriesRepository industriesRepository){
        this.industriesRepository = industriesRepository;
    }

    @RequestMapping("/all")
    public List<Industry> getAll(){
        return industriesRepository.findAll();
    }
}
