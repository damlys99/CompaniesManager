package com.example.uniprogramming;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsersRepository extends JpaRepository<User, Long>{
    List<User> findByIdGreaterThanEqualAndIdLessThan(long more, long less);
}