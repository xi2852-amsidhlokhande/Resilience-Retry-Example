package com.amsidh.mvc.controller;

import com.amsidh.mvc.domain.ResponseService2;
import com.amsidh.mvc.domain.ResponseService3;
import com.amsidh.mvc.entities.Service2;
import com.amsidh.mvc.service.Service2Service;
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
@RequestMapping("/service2")
@Slf4j
public class Service2Controller {

    private final Service2Service service2Service;

    @GetMapping
    public List<ResponseService2> findAllService2() {
        log.info("Inside findAllService2 method of Service2Controller");
        return service2Service.getAllService2().stream().map(service2 -> ResponseService2.builder()
                        .service3(getResponseService3(service2.getService2Id()))
                        .service2Id(service2.getService2Id())
                        .service2Message(service2.getService2Message())
                        .build())
                .collect(Collectors.toList());
    }

    @GetMapping("/{service2Id}")
    public ResponseService2 getService2ById(@PathVariable("service2Id") Integer service2Id) {
        log.info("Inside getService2ById method of Service2Controller");
        Service2 service2 = service2Service.getService2ById(service2Id);
        return ResponseService2.builder()
                .service3(getResponseService3(service2.getService2Id()))
                .service2Id(service2.getService2Id())
                .service2Message(service2.getService2Message())
                .build();
    }

    @PostMapping
    public ResponseService2 saveService2(@RequestBody @Valid Service2 service2) {
        log.info("Inside saveService2 method of Service2Controller");
        Service2 service2Saved = service2Service.saveService2(service2);
        return ResponseService2.builder()
                .service3(null)
                .service2Id(service2Saved.getService2Id())
                .service2Message(service2Saved.getService2Message())
                .build();
    }

    private final RestTemplate restTemplate;
    private final static String SERVICE3_URL = "http://localhost:8083/service3";

    @Retry(name = "service3")
    @CircuitBreaker(name = "service3")
    public ResponseService3 getResponseService3(Integer service3Id) {
        log.info("Inside getResponseService3 method calling MicroService-3 API");
        ResponseEntity<ResponseService3> responseService3ResponseEntity = this.restTemplate.getForEntity(SERVICE3_URL + "/" + service3Id, ResponseService3.class);
        log.info("Inside getResponseService3 method and Response received from MicroService-3");
        return responseService3ResponseEntity.getBody();
    }

    @Retry(name = "service3")
    @CircuitBreaker(name = "service3")
    public List<ResponseService3> getResponseService3s() {
        log.info("Inside getResponseService3s method calling MicroService-3 API");
        ResponseEntity<ResponseService3[]> response = restTemplate.getForEntity(SERVICE3_URL, ResponseService3[].class);
        log.info("Inside getResponseService3s method and Response received from MicroService-3");
        return Arrays.asList(response.getBody());
    }

}
