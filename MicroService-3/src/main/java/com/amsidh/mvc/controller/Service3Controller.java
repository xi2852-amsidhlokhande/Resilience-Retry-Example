package com.amsidh.mvc.controller;

import com.amsidh.mvc.domain.ResponseService3;
import com.amsidh.mvc.domain.ResponseService4;
import com.amsidh.mvc.entities.Service3;
import com.amsidh.mvc.service.Service3Service;
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
@RequestMapping("/service3")
@Slf4j
public class Service3Controller {

    private final Service3Service service3Service;

    @GetMapping
    public List<ResponseService3> findAllService1() {
        log.info("Inside findAllService1 method of Service3Controller");
        return service3Service.getAllService3().stream().map(service3 -> ResponseService3.builder()
                        .service4(getResponseService4(service3.getService3Id()))
                        .service3Id(service3.getService3Id())
                        .service3Message(service3.getService3Message())
                        .build())
                .collect(Collectors.toList());
    }

    @GetMapping("/{service3Id}")
    public ResponseService3 getService3ById(@PathVariable("service3Id") Integer service3Id) {
        log.info("Inside getService3ById method of Service3Controller");
        Service3 service3 = service3Service.getService3ById(service3Id);
        return ResponseService3.builder()
                .service4(getResponseService4(service3.getService3Id()))
                .service3Id(service3.getService3Id())
                .service3Message(service3.getService3Message())
                .build();
    }

    @PostMapping
    public ResponseService3 saveService3(@RequestBody @Valid Service3 service3) {
        log.info("Inside saveService3 method of Service3Controller");
        Service3 service3Saved = service3Service.saveService3(service3);
        return ResponseService3.builder()
                .service4(null)
                .service3Id(service3Saved.getService3Id())
                .service3Message(service3Saved.getService3Message())
                .build();
    }

    private final RestTemplate restTemplate;
    private final static String SERVICE4_URL = "http://localhost:8084/service4";

    @Retry(name = "service4")
    @CircuitBreaker(name = "service4")
    public ResponseService4 getResponseService4(Integer service4Id) {
        log.info("Inside getResponseService4 method calling MicroService-4 API");
        ResponseEntity<ResponseService4> responseService4ResponseEntity = this.restTemplate.getForEntity(SERVICE4_URL + "/" + service4Id, ResponseService4.class);
        log.info("Inside getResponseService4 method and Response received from MicroService-4");
        return responseService4ResponseEntity.getBody();
    }

    @Retry(name = "service4")
    @CircuitBreaker(name = "service4")
    public List<ResponseService4> getResponseService4s() {
        log.info("Inside getResponseService4s method calling MicroService-4 API");
        ResponseEntity<ResponseService4[]> response = restTemplate.getForEntity(SERVICE4_URL, ResponseService4[].class);
        log.info("Inside getResponseService4s method and Response received from MicroService-4");
        return Arrays.asList(response.getBody());
    }

}
