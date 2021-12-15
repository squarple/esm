package com.epam.esm.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;

@SpringBootApplication(scanBasePackages = "com.epam.esm")
@Profile("dev")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
