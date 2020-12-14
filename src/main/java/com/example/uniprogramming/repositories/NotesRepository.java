package com.example.uniprogramming.repositories;

import com.example.uniprogramming.models.Company;
import com.example.uniprogramming.models.Note;
import com.example.uniprogramming.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotesRepository extends JpaRepository<Note, Long> {
    Note findById(long id);
    List<Note> findAll();
    List<Note> findAllByIsDeletedIsFalse();
    List<Note> findAllByIsDeletedIsFalseAndUser(User user);
    List<Note> findAllByIsDeletedIsFalseAndCompany(Company company);
}
