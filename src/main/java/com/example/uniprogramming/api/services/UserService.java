package com.example.uniprogramming.api.services;

import com.example.uniprogramming.models.RolesRepository;
import com.example.uniprogramming.models.User;
import com.example.uniprogramming.models.UsersRepository;
import com.example.uniprogramming.security.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {

    private final UsersRepository usersRepository;
    private final RolesRepository rolesRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final MyUserDetailsService userDetailsService;
    @Autowired
    public UserService(UsersRepository usersRepository, RolesRepository rolesRepository, BCryptPasswordEncoder passwordEncoder, MyUserDetailsService userDetailsService){
        this.usersRepository = usersRepository;
        this.rolesRepository = rolesRepository;
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
    }

    public List<User> getAll(){
        return usersRepository.findAllByIsDeletedIsFalse();
    }

    public Optional<User> getUser(String username){
        return usersRepository.findByUserName(username);
    }

    public User getUser(int id){
        return usersRepository.findById(id);
    }

    public User getLoggedUser(){
        return usersRepository.findByUserName(userDetailsService.getLoggedUser().getUsername()).get();
    }

    public Long count(){
        return usersRepository.countByIsDeletedIsFalse();
    }

    public User deleteUser(int id){

        User userGettingDeleted = usersRepository.findById(id);
        userGettingDeleted.setDeleted(true);
        usersRepository.save(userGettingDeleted);
        return userGettingDeleted;
    }

    public User addUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        usersRepository.save(user);
        return user;
    }

    public User modifyUser(int id, User user){
        User userGettingModified = usersRepository.findById(id);
        userGettingModified.setName(user.getName());
        userGettingModified.setSurname(user.getSurname());
        userGettingModified.setUserName(user.getUserName());
        userGettingModified.setDateOfBirth(user.getDateOfBirth());
        usersRepository.save(userGettingModified);
        return userGettingModified;
    }

    public User setRole(int id, String role){
        User user = usersRepository.findById(id);
        user.setRole(rolesRepository.findByName(role));
        usersRepository.save(user);
        return user;
    }

    public List<User> getPage(int currentPage, int pageSize){
        Pageable userPage = PageRequest.of(currentPage - 1, pageSize);
        return usersRepository.findAllByIsDeletedIsFalse(userPage);
    }

}
