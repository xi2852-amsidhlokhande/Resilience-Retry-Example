package com.amsidh.mvc.controller;

import com.amsidh.mvc.domain.ResponseService4;
import com.amsidh.mvc.domain.ResponseService5;
import com.amsidh.mvc.entities.Service4;
import com.amsidh.mvc.service.Service4Service;
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
@RequestMapping("/service4")
@Slf4j
public class Service4Controller {

    private final Service4Service service4Service;

    @GetMapping
    public List<ResponseService4> findAllService4() {
        log.info("Inside findAllService4 method of Service4Controller");
        return service4Service.getAllService4().stream()
                .map(service4 -> ResponseService4.builder()
                        .service5(getResponseService5(service4.getService4Id()))
                        .service4Id(service4.getService4Id())
                        .service4Message(service4.getService4Message())
                        .build())
                .collect(Collectors.toList());
    }

    @GetMapping("/{service4Id}")
    public ResponseService4 getService4ById(@PathVariable("service4Id") Integer service4Id) {
        log.info("Inside getService4ById method of Service4Controller");
        Service4 service4 = service4Service.getService4ById(service4Id);
        return ResponseService4.builder().service4Id(service4.getService4Id()).service4Message(service4.getService4Message())
                .service5(getResponseService5(service4.getService4Id()))
                .build();
    }

    @PostMapping
    public ResponseService4 saveService4(@RequestBody @Valid Service4 service4) {
        log.info("Inside saveService4 method of Service4Controller");
        Service4 service4Saved = service4Service.saveService4(service4);
        return ResponseService4.builder().service4Id(service4Saved.getService4Id()).service4Message(service4Saved.getService4Message())
                .service5(null)
                .build();
    }

    private final RestTemplate restTemplate;
    private final static String SERVICE5_URL = "http://localhost:8085/service5";

    @Retry(name = "service5")
    @CircuitBreaker(name = "service5")
    public ResponseService5 getResponseService5(Integer service5Id) {
        log.info("Inside getResponseService5 method calling MicroService-5 API");
        ResponseEntity<ResponseService5> responseService5ResponseEntity = this.restTemplate.getForEntity(SERVICE5_URL + "/" + service5Id, ResponseService5.class);
        log.info("Inside getResponseService5 method and Response received from MicroService-5");
        return responseService5ResponseEntity.getBody();
    }

    @Retry(name = "service5")
    @CircuitBreaker(name = "service5")
    public List<ResponseService5> getResponseService5s() {
        log.info("Inside getResponseService5s method calling MicroService-5 API");
        ResponseEntity<ResponseService5[]> response = restTemplate.getForEntity(SERVICE5_URL, ResponseService5[].class);
        log.info("Inside getResponseService5s method and Response received from MicroService-5");
        return Arrays.asList(response.getBody());
    }
}
