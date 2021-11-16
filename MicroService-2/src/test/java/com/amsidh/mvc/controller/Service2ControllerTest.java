package com.amsidh.mvc.controller;

import com.amsidh.mvc.domain.ResponseService2;
import com.amsidh.mvc.entities.Service2;
import com.amsidh.mvc.service.Service2Service;
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
public class Service2ControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @MockBean
    private Service2Service service2Service;


    @RepeatedTest(10)
    public void test(RepetitionInfo repetitionInfo) {

        Mockito.when(service2Service.getService2ById(any(Integer.class))).thenReturn(Service2.builder().service2Id(1).service2Message("Service2 Message1").build());

        ResponseEntity<ResponseService2> responseService2ResponseEntity = this.testRestTemplate.getForEntity("http://localhost:" + port + "/service2/1", ResponseService2.class);
        log.info(responseService2ResponseEntity.getBody().toString());
        Assertions.assertNotNull(responseService2ResponseEntity.getBody());
    }
}
