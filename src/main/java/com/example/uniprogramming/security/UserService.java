package com.example.uniprogramming.security;

import com.example.uniprogramming.User;
import com.example.uniprogramming.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Date;

@Service
public class UserService {

    private final UsersRepository usersRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UsersRepository usersRepository, BCryptPasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;

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
        user.setRoles("ROLE_USER");

        return save(user);
    }
}
