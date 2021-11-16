package com.amsidh.mvc.controller;

import com.amsidh.mvc.domain.ResponseService1;
import com.amsidh.mvc.entities.Service1;
import com.amsidh.mvc.service.Service1Service;
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
public class Service1ControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @MockBean
    private Service1Service service1Service;


    @RepeatedTest(10)
    public void test(RepetitionInfo repetitionInfo) {

        Mockito.when(service1Service.getService1ById(any(Integer.class))).thenReturn(Service1.builder().service1Id(1).service1Message("Service1 Message1").build());

        ResponseEntity<ResponseService1> responseService1ResponseEntity = this.testRestTemplate.getForEntity("http://localhost:" + port + "/service1/1", ResponseService1.class);
        log.info(responseService1ResponseEntity.getBody().toString());
        Assertions.assertNotNull(responseService1ResponseEntity.getBody());
    }
}
