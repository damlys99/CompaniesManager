package com.example.uniprogramming.repositories;

import com.example.uniprogramming.models.Company;
import com.example.uniprogramming.models.User;
import com.example.uniprogramming.search.CustomCompaniesRepository;
import com.example.uniprogramming.search.CompanyDateOnly;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompaniesRepository extends JpaRepository<Company, Long>, CustomCompaniesRepository {

    List<Company> findAllByIsDeletedIsFalse();
    Company findById(long id);
    Optional<Company> findByName(String companyName);
    List<Company> findAllByIsDeletedIsFalse(Pageable page);
    Long countByIsDeletedIsFalse();
    List<Company> findAllByIsDeletedIsFalseAndUser(User user);
    List<CompanyDateOnly> findDistinctByAddedNotNull();

    List<Company> getFiltered(List<String> industries, List<String> dates, Pageable pageable);
    Long getCount(List<String> industries, List<String> dates);



}
