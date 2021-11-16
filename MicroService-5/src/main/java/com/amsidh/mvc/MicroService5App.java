package com.amsidh.mvc;

import com.amsidh.mvc.entities.Service5;
import com.amsidh.mvc.repository.Service5Repository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@RequiredArgsConstructor
@SpringBootApplication
public class MicroService5App implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(MicroService5App.class, args);
    }

    private final Service5Repository Service5Repository;

    @Override
    public void run(String... args) throws Exception {
        Service5Repository.saveAll(Arrays.asList(
                Service5.builder().service5Message("Service5 Message1").build(),
                Service5.builder().service5Message("Service5 Message2").build(),
                Service5.builder().service5Message("Service5 Message3").build()
        ));
    }
}
