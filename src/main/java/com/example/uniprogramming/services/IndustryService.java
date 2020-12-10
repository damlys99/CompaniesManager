package com.example.uniprogramming.services;

import com.example.uniprogramming.models.Industry;
import com.example.uniprogramming.repositories.IndustriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IndustryService {

    private final IndustriesRepository industriesRepository;

    @Autowired
    public IndustryService(IndustriesRepository industriesRepository){
        this.industriesRepository = industriesRepository;
    }

    public Industry getIndustry(String name){
        return industriesRepository.findByName(name);
    }
}
