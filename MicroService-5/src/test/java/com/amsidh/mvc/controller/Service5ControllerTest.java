package com.amsidh.mvc.controller;

import com.amsidh.mvc.domain.ResponseService5;
import com.amsidh.mvc.entities.Service5;
import com.amsidh.mvc.service.Service5Service;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import static org.mockito.ArgumentMatchers.any;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class Service5ControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @MockBean
    private Service5Service service5Service;


    @RepeatedTest(10)
    public void test(RepetitionInfo repetitionInfo) {

        Mockito.when(service5Service.getService5ById(any(Integer.class))).thenReturn(Service5.builder().service5Id(1).service5Message("Service5 Message1").build());

        ResponseEntity<ResponseService5> responseService5ResponseEntity = this.testRestTemplate.getForEntity("http://localhost:" + port + "/service5/1", ResponseService5.class);
        log.info(responseService5ResponseEntity.getBody().toString());
        Assertions.assertNotNull(responseService5ResponseEntity.getBody());
    }
}
