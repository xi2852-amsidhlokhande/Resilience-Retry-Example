package com.amsidh.mvc;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@RequiredArgsConstructor
@SpringBootApplication
public class MicroService1App {

    public static void main(String[] args) {
        SpringApplication.run(MicroService1App.class, args);
    }

}
