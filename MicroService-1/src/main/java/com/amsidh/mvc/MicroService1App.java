package com.amsidh.mvc;

import com.amsidh.mvc.entities.Service1;
import com.amsidh.mvc.repository.Service1Repository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@RequiredArgsConstructor
@SpringBootApplication
public class MicroService1App implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(MicroService1App.class, args);
    }

    private final Service1Repository service1Repository;

    @Override
    public void run(String... args) throws Exception {
        service1Repository.saveAll(Arrays.asList(
                Service1.builder().service1Message("Service1 Message1").build(),
                Service1.builder().service1Message("Service1 Message2").build(),
                Service1.builder().service1Message("Service1 Message3").build()
        ));
    }
}
