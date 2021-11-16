package com.amsidh.mvc.controller;

import com.amsidh.mvc.domain.ResponseService4;
import com.amsidh.mvc.entities.Service4;
import com.amsidh.mvc.service.Service4Service;
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
public class Service4ControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @MockBean
    private Service4Service service4Service;


    @RepeatedTest(10)
    public void test(RepetitionInfo repetitionInfo) {

        Mockito.when(service4Service.getService4ById(any(Integer.class))).thenReturn(Service4.builder().service4Id(1).service4Message("Service4 Message1").build());

        ResponseEntity<ResponseService4> responseService4ResponseEntity = this.testRestTemplate.getForEntity("http://localhost:" + port + "/service4/1", ResponseService4.class);
        log.info(responseService4ResponseEntity.getBody().toString());
        Assertions.assertNotNull(responseService4ResponseEntity.getBody());
    }
}
