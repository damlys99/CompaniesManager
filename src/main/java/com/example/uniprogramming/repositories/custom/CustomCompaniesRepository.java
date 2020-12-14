package com.example.uniprogramming.repositories.custom;

import com.example.uniprogramming.models.Company;

import java.util.List;

public interface CustomCompaniesRepository {
    public List<Company> getFiltered();
}
