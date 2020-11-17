package com.example.uniprogramming;

import com.example.uniprogramming.models.Role;
import com.example.uniprogramming.models.RolesRepository;
import com.example.uniprogramming.models.User;
import com.example.uniprogramming.models.UsersRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Date;
import java.util.*;

@Component
public class DatabaseSeeder implements CommandLineRunner {
    private final UsersRepository usersRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final RolesRepository rolesRepository;

    private static final Logger log = LoggerFactory.getLogger(ViewController.class);

    @Autowired
    public DatabaseSeeder(UsersRepository usersRepository, BCryptPasswordEncoder passwordEncoder, RolesRepository rolesRepository) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
        this.rolesRepository = rolesRepository;
    }

    @Override
    public void run(String... args) throws Exception {
/*    User user = usersRepository.findByUserName("admin").get();
    user.addToRoles(rolesRepository.findByName("ROLE_USER"));
    user.addToRoles(rolesRepository.findByName("ROLE_MODERATOR"));
    usersRepository.save(user);*/
/*

        Random generator = new Random();
        List<User> users = new ArrayList<>();

             for (int i = 0; i <= 100; i++) {
            String name = Files.readAllLines(Paths.get("src/main/resources/static/names.txt").toAbsolutePath())
                    .get(generator.nextInt(100));
            String surname = Files.readAllLines(Paths.get("src/main/resources/static/surnames.txt").toAbsolutePath())
                    .get(generator.nextInt(100));
            Date dateOfBirth = Date.valueOf("" + (generator.nextInt(100) + 1900) + '-' +
                    (generator.nextInt(12) + 1) + '-' + (generator.nextInt(28) + 1));
            String username = name + surname.substring(0, 3) + dateOfBirth.toString().substring(2, 4);
            User user = new User(name, surname, dateOfBirth, username, passwordEncoder.encode(username));
            user.addToRoles(rolesRepository.findByName("ROLE_USER"));
            users.add(user);
        }
        usersRepository.saveAll(users);


*/

    /*List<User> users = new ArrayList<>();
    users.add(new User("Damian", "Łyszkiewicz", Date.valueOf("1999-12-14"), "admin", "admin", "ROLE_ADMIN, ROLE_USER"));
    usersRepository.saveAll(users);]*/


    }
}