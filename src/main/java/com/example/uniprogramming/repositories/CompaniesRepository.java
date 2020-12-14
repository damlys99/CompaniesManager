package com.example.uniprogramming.repositories;

import com.example.uniprogramming.models.Company;
import com.example.uniprogramming.models.User;
import com.example.uniprogramming.search.CompanyDateOnly;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
public interface CompaniesRepository extends JpaRepository<Company, Long> {

    List<Company> findAllByIsDeletedIsFalse();
    Company findById(long id);
    Optional<Company> findByName(String companyName);
    List<Company> findAllByIsDeletedIsFalse(Pageable page);
    Long countByIsDeletedIsFalse();
    List<Company> findAllByIsDeletedIsFalseAndUser(User user);
    List<CompanyDateOnly> findAddedByAddedNotNull();

    public List<Company> findFiltered(String name);
}
