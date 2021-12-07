package com.amsidh.mvc;

import com.amsidh.mvc.entities.Service3;
import com.amsidh.mvc.repository.Service3Repository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@RequiredArgsConstructor
@SpringBootApplication
public class MicroService3App implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(MicroService3App.class, args);
	}

	private final Service3Repository Service3Repository;

	@Override
	public void run(String... args) throws Exception {
		Service3Repository.saveAll(Arrays.asList(
				Service3.builder().service3Message("Service3 Message1").build(),
				Service3.builder().service3Message("Service3 Message2").build(),
				Service3.builder().service3Message("Service3 Message3").build()
		));
	}
}
