package com.amsidh.mvc.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class Service3ControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;



    @RepeatedTest(10)
    public void test(RepetitionInfo repetitionInfo) {


        ResponseEntity<String> responseService1ResponseEntity = this.testRestTemplate.getForEntity("http://localhost:" + port + "/service1/1", String.class);
        log.info(responseService1ResponseEntity.getBody().toString());
        Assertions.assertNotNull(responseService1ResponseEntity.getBody());
    }
}
