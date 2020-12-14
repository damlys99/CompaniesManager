package com.example.uniprogramming.services;

import com.example.uniprogramming.models.User;
import com.example.uniprogramming.repositories.RolesRepository;
import com.example.uniprogramming.repositories.UsersRepository;
import com.example.uniprogramming.security.services.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public User getUser(long id){
        return usersRepository.findById(id);
    }

    public User getLoggedUser(){
        return usersRepository.findByUserName(userDetailsService.getLoggedUser().getUsername()).orElseThrow();
    }

    public Long count(){
        return usersRepository.countByIsDeletedIsFalse();
    }

    public User deleteUser(int id){

        User userGettingDeleted = getUser(id);
        userGettingDeleted.setDeleted(true);
        return saveUser(userGettingDeleted);
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

    public boolean userExists(String username){
        return getUser(username).isPresent();
    }

    public User saveUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        usersRepository.save(user);
        return user;
    }

}
