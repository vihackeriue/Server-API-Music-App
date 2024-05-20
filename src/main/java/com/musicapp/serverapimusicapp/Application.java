package com.musicapp.serverapimusicapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

import java.nio.file.Path;
import java.nio.file.Paths;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
public class Application {

    public static void main(String[] args) {
        Path path = Paths.get("audio");
        System.out.println(path);
        SpringApplication.run(Application.class, args);

    }
}
