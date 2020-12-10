package com.example.uniprogramming.services;

import com.example.uniprogramming.repositories.IndustriesRepository;
import com.example.uniprogramming.models.Company;
import com.example.uniprogramming.repositories.CompaniesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CompanyService {

    private final CompaniesRepository companiesRepository;
    private final IndustriesRepository industriesRepository;

    @Autowired
    public CompanyService(CompaniesRepository companiesRepository, IndustriesRepository industriesRepository){
        this.companiesRepository = companiesRepository;
        this.industriesRepository = industriesRepository;

    }

    public List<Company> getAll(){
        return companiesRepository.findAllByIsDeletedIsFalse();
    }

    public Optional<Company> getCompany(String companyname){
        return companiesRepository.findByName(companyname);
    }

    public Company getCompany(int id){
        return companiesRepository.findById(id);
    }

    public Long count(){
        return companiesRepository.countByIsDeletedIsFalse();
    }

    public Company deleteCompany(int id){

        Company companyGettingDeleted = companiesRepository.findById(id);
        companyGettingDeleted.setDeleted(true);
        companiesRepository.save(companyGettingDeleted);
        return companyGettingDeleted;
    }

    public Company addCompany(Company company){
        companiesRepository.save(company);
        return company;
    }

    public Company modifyCompany(int id, Company company){
        Company companyGettingModified = companiesRepository.findById(id);
        companyGettingModified.setName(company.getName());
        companyGettingModified.setAddress(company.getAddress());
        companyGettingModified.setCity(company.getCity());
        companiesRepository.save(companyGettingModified);
        return companyGettingModified;
    }

    public Company setIndustry(int id, String industry){
        Company company = companiesRepository.findById(id);
        company.setIndustry(industriesRepository.findByName(industry));
        companiesRepository.save(company);
        return company;
    }

    public List<Company> getPage(int currentPage, int pageSize){
        Pageable companyPage = PageRequest.of(currentPage - 1, pageSize);
        return companiesRepository.findAllByIsDeletedIsFalse(companyPage);
    }

}

