package com.amsidh.mvc;

import com.amsidh.mvc.entities.Service2;
import com.amsidh.mvc.repository.Service2Repository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@RequiredArgsConstructor
@SpringBootApplication
public class MicroService2App implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(MicroService2App.class, args);
	}

	private final Service2Repository Service2Repository;

	@Override
	public void run(String... args) throws Exception {
		Service2Repository.saveAll(Arrays.asList(
				Service2.builder().service2Message("Service2 Message1").build(),
				Service2.builder().service2Message("Service2 Message2").build(),
				Service2.builder().service2Message("Service2 Message3").build()
		));
	}
}
