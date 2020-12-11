package com.example.uniprogramming.services;

import com.example.uniprogramming.dto.CompanyDTO;
import com.example.uniprogramming.dto.CompanyDTOService;
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

    public boolean companyExists(String companyname){
        return getCompany(companyname).isPresent();
    }

    public Company getCompany(long id){
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

    public Company saveCompany(Company company){
        companiesRepository.save(company);
        return company;
    }

/*    public Company modifyCompany(Company company){
        Company companyModified = companiesRepository.findById(company.getId());
        companyModified.setName(company.getName());
        companyModified.setNip(company.getNip());
        companyModified.setAddress(company.getAddress());
        comp
    }*/

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

