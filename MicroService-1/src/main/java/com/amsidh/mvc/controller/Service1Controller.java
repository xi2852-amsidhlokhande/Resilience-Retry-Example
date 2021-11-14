package com.amsidh.mvc.controller;

import com.amsidh.mvc.domain.ResponseService1;
import com.amsidh.mvc.domain.ResponseService2;
import com.amsidh.mvc.entities.Service1;
import com.amsidh.mvc.service.Service1Service;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/service1")
@Slf4j
public class Service1Controller {

    private final Service1Service service1Service;

    @Retry(name = "service2")
    @CircuitBreaker(name = "service2")
    @GetMapping
    public List<ResponseService1> findAllService1() {
        log.info("Inside findAllService1 method of Service1Controller");
        return service1Service.getAllService1()
                .stream().map(service1 -> ResponseService1.builder()
                        .service2(getResponseService2(service1.getService1Id()))
                        .service1Id(service1.getService1Id())
                        .service1Message(service1.getService1Message())
                        .build())
                .collect(Collectors.toList());
    }

    @Retry(name = "service2")
    @CircuitBreaker(name = "service2")
    @GetMapping("/{service1Id}")
    public ResponseService1 getService1ById(@PathVariable("service1Id") Integer service1Id) {
        log.info("Inside getService1ById method of Service1Controller");
        Service1 service1 = service1Service.getService1ById(service1Id);
        return ResponseService1.builder().service2(getResponseService2(service1.getService1Id()))
                .service1Id(service1.getService1Id())
                .service1Message(service1.getService1Message())
                .build();
    }

    @PostMapping
    public ResponseService1 saveService1(@RequestBody @Valid Service1 service1) {
        log.info("Inside saveService1 method of Service1Controller");
        Service1 service1Saved = service1Service.saveService1(service1);
        return ResponseService1.builder()
                .service2(null)
                .service1Id(service1Saved.getService1Id())
                .service1Message(service1Saved.getService1Message())
                .build();
    }

    private final RestTemplate restTemplate;
    private final static String SERVICE2_URL = "http://localhost:8082/service2";

    @Retry(name = "service2", fallbackMethod = "getResponseService2Fallback")
    @CircuitBreaker(name = "service2", fallbackMethod = "getResponseService2Fallback")
    public ResponseService2 getResponseService2(Integer service2Id) {
        log.info("Inside getResponseService2 method calling MicroService-2 API");
        ResponseEntity<ResponseService2> responseService2ResponseEntity = this.restTemplate.getForEntity(SERVICE2_URL + "/" + service2Id, ResponseService2.class);
        log.info("Inside getResponseService2 method and Response received from MicroService-2");
        return responseService2ResponseEntity.getBody();
    }

    @Retry(name = "service2")
    @CircuitBreaker(name = "service2")
    public List<ResponseService2> getResponseService2s() {
        log.info("Inside getResponseService2s method calling MicroService-2 API");
        ResponseEntity<ResponseService2[]> response = restTemplate.getForEntity(SERVICE2_URL, ResponseService2[].class);
        log.info("Inside getResponseService2s method and Response received from MicroService-2");
        return Arrays.asList(response.getBody());
    }

    public ResponseService2 getResponseService2Fallback(Exception exception, Integer service2Id) {
        log.error(exception.getLocalizedMessage());
        return null;
    }
}
