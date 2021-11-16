package com.amsidh.mvc.controller;

import com.amsidh.mvc.domain.ResponseService3;
import com.amsidh.mvc.entities.Service3;
import com.amsidh.mvc.service.Service3Service;
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
public class Service3ControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @MockBean
    private Service3Service service3Service;


    @RepeatedTest(10)
    public void test(RepetitionInfo repetitionInfo) {

        Mockito.when(service3Service.getService3ById(any(Integer.class))).thenReturn(Service3.builder().service3Id(1).service3Message("Service3 Message1").build());

        ResponseEntity<ResponseService3> responseService3ResponseEntity = this.testRestTemplate.getForEntity("http://localhost:" + port + "/service3/1", ResponseService3.class);
        log.info(responseService3ResponseEntity.getBody().toString());
        Assertions.assertNotNull(responseService3ResponseEntity.getBody());
    }
}
