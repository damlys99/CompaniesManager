package com.example.uniprogramming.security.services;

import com.example.uniprogramming.models.User;
import com.example.uniprogramming.repositories.RolesRepository;
import com.example.uniprogramming.repositories.UsersRepository;
import com.example.uniprogramming.security.models.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Date;

@Service
public class UserDTOService {

    private final UsersRepository usersRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final RolesRepository rolesRepository;

    @Autowired
    public UserDTOService(UsersRepository usersRepository, BCryptPasswordEncoder passwordEncoder, RolesRepository rolesRepository) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
        this.rolesRepository = rolesRepository;

    }

    public boolean userExists(String userName) {
        return usersRepository.findByUserName(userName).isPresent();
    }

    public User save(User user) {
        return usersRepository.save(user);
    }

    public User register(UserDTO userDTO) {
        User user = new User();
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setName(userDTO.getName());
        user.setSurname(userDTO.getSurname());
        user.setUserName(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setDateOfBirth(Date.valueOf(userDTO.getDateOfBirth()));
        user.setRole(rolesRepository.findByName("ROLE_USER"));

        return save(user);
    }
}
