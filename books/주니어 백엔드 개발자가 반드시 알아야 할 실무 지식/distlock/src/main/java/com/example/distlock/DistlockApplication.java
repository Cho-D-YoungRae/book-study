package com.example.distlock;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DistlockApplication {

    public static void main(String[] args) {
        SpringApplication.run(DistlockApplication.class, args);
    }

    @Bean
    public ApplicationRunner applicationRunner() {
        return args -> {

        };
    }

}
