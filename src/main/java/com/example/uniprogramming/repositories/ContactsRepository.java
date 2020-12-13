package com.example.uniprogramming.repositories;

import com.example.uniprogramming.models.Company;
import com.example.uniprogramming.models.Contact;
import com.example.uniprogramming.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContactsRepository extends JpaRepository<Contact, Long> {
    Optional<Contact> findByName(String name);
    Contact findById(long id);
    List<Contact> findAll();
    List<Contact> findAllByIsDeletedIsFalse();
    List<Contact> findAllByIsDeletedIsFalseAndUser(User user);
    List<Contact> findAllByIsDeletedIsFalseAndCompany(Company company);
}
