package com.example.printemps;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BibliothequePrintempsApplication {

    public static void main(String[] args) {
        SpringApplication.run(BibliothequePrintempsApplication.class, args);
    }
}