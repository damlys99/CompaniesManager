package com.example.uniprogramming;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

@Component
public class DatabaseSeeder implements CommandLineRunner {
    private UsersRepository usersRepository;

    @Autowired
    public DatabaseSeeder(UsersRepository usersRepository){
        this.usersRepository = usersRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        List<User> users = new ArrayList<>();
        Random generator = new Random();
        for(int i = 0; i<=100; i++){
            users.add(new User(
                    Files.readAllLines(Paths.get("src/main/resources/static/names.txt").toAbsolutePath())
                            .get(generator.nextInt(100)),
                    Files.readAllLines(Paths.get("src/main/resources/static/surnames.txt").toAbsolutePath()).
                            get(generator.nextInt(100)),
                    Date.valueOf("" + (generator.nextInt(100) + 1900) + '-' +
                            (generator.nextInt(12) + 1 ) + '-' + (generator.nextInt(28) + 1 ))
            )
            );
        }
        System.out.println();
        usersRepository.saveAll(users);
    }
}
