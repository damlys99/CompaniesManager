package com.example.uniprogramming;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;

@Component
public class DatabaseSeeder implements CommandLineRunner {
    private final UsersRepository usersRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private static final Logger log = LoggerFactory.getLogger(ViewController.class);

    @Autowired
    public DatabaseSeeder(UsersRepository usersRepository, BCryptPasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        List<User> users = new ArrayList<>();
        Random generator = new Random();

        for (int i = 0; i <= 100; i++) {
            String name = Files.readAllLines(Paths.get("src/main/resources/static/names.txt").toAbsolutePath())
                    .get(generator.nextInt(100));
            String surname = Files.readAllLines(Paths.get("src/main/resources/static/surnames.txt").toAbsolutePath())
                    .get(generator.nextInt(100));
            Date dateOfBirth = Date.valueOf("" + (generator.nextInt(100) + 1900) + '-' +
                    (generator.nextInt(12) + 1) + '-' + (generator.nextInt(28) + 1));
            String username = name + surname.substring(0, 3) + dateOfBirth.toString().substring(2, 4);


            users.add(new User(name, surname, dateOfBirth, username, passwordEncoder.encode(username), "ROLE_USER"));
        }
        usersRepository.saveAll(users);

/*    List<User> users = new ArrayList<>();
    users.add(new User("Damian", "Łyszkiewicz", Date.valueOf("1999-12-14"), "admin", "admin", "ROLE_ADMIN, ROLE_USER"));
    usersRepository.saveAll(users);]*/


    }
}