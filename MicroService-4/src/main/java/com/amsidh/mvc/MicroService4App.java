package com.amsidh.mvc;

import com.amsidh.mvc.entities.Service4;
import com.amsidh.mvc.repository.Service4Repository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@RequiredArgsConstructor
@SpringBootApplication
public class MicroService4App implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(MicroService4App.class, args);
    }

    private final Service4Repository Service4Repository;

    @Override
    public void run(String... args) throws Exception {
        Service4Repository.saveAll(Arrays.asList(
                Service4.builder().service4Message("Service4 Message1").build(),
                Service4.builder().service4Message("Service4 Message2").build(),
                Service4.builder().service4Message("Service4 Message3").build()
        ));
    }
}
