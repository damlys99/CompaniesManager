package com.example.uniprogramming.services;

import com.example.uniprogramming.models.Company;
import com.example.uniprogramming.models.User;
import com.example.uniprogramming.repositories.CompaniesRepository;
import com.example.uniprogramming.repositories.IndustriesRepository;
import com.example.uniprogramming.search.CompanyDateOnly;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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

    public Long count(List<String> industries, List<String> dates){
        return companiesRepository.getCount(industries,dates);
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


    public List<Company> getPage(int currentPage, int pageSize, List<String> industries, List<String> dates){
        Pageable companyPage = PageRequest.of(currentPage - 1, pageSize);
        return companiesRepository.getFiltered(industries, dates, companyPage);
    }


    public List<Company> getByUser(User user){
        return companiesRepository.findAllByIsDeletedIsFalseAndUser(user);
    }

    public List<CompanyDateOnly> getDates(){
        return companiesRepository.findDistinctByAddedNotNull();
    }

}

