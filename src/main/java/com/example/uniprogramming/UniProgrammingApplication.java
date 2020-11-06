package com.example.uniprogramming;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
@SpringBootApplication
public class UniProgrammingApplication {

    public static void main(String[] args) {
        SpringApplication.run(UniProgrammingApplication.class, args);
    }

}
