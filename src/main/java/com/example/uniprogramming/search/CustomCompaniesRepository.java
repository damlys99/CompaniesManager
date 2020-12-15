package com.example.uniprogramming.search;

import com.example.uniprogramming.models.Company;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomCompaniesRepository {
    public List<Company> getFiltered(List<String> industries, List<String> dates, Pageable pageable);
    public Long getCount(List<String> industries, List<String> dates);
}
