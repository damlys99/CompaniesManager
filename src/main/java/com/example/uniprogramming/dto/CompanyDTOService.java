package com.example.uniprogramming.dto;

import com.example.uniprogramming.models.Company;
import com.example.uniprogramming.services.CompanyService;
import com.example.uniprogramming.services.IndustryService;
import com.example.uniprogramming.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;

@Service
public class CompanyDTOService {
    private final UserService userService;
    private final IndustryService industryService;
    private final CompanyService companyService;

    @Autowired
    public CompanyDTOService(UserService userService, IndustryService industryService, CompanyService companyService){
        this.userService = userService;
        this.industryService = industryService;
        this.companyService = companyService;
    }

    public Company modifyCompany(CompanyDTO companyDTO){
        Company company = companyService.getCompany(companyDTO.getId());
        company.setName(companyDTO.getName());
        company.setNip(companyDTO.getNip());
        company.setAddress(companyDTO.getAddress());
        company.setCity(companyDTO.getCity());
        company.setIndustry(industryService.getIndustry(companyDTO.getIndustry()));
        companyService.saveCompany(company);
        return company;
    }

}
