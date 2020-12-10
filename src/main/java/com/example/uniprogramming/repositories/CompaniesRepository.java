package com.example.uniprogramming.repositories;

import com.example.uniprogramming.models.Company;
import com.example.uniprogramming.models.Company;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompaniesRepository extends JpaRepository<Company, Long> {
    List<Company> findAllByIsDeletedIsFalse();
    Company findById(long id);
    Optional<Company> findByName(String companyName);
    List<Company> findAllByIsDeletedIsFalse(Pageable page);
    Long countByIsDeletedIsFalse();
}
