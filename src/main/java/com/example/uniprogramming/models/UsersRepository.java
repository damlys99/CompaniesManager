package com.example.uniprogramming.models;

import com.example.uniprogramming.models.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<User, Long>{
    List<User> findAllByIsDeletedIsFalse();
    User findById(long id);
    Optional<User> findByUserName(String userName);
    List<User> findAllByIsDeletedIsFalse(Pageable page);
    Long countByIsDeletedIsFalse();
}