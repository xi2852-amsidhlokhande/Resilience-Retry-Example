package com.amsidh.mvc.controller;

import com.amsidh.mvc.domain.ResponseService4;
import com.amsidh.mvc.domain.ResponseService5;
import com.amsidh.mvc.entities.Service4;
import com.amsidh.mvc.service.Service4Service;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.decorators.Decorators;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryRegistry;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.function.Supplier;

@RequiredArgsConstructor
@RestController
@RequestMapping("/service4")
@Slf4j
public class Service4Controller {

    private final static String SERVICE5_URL = "http://localhost:8085/service5";
    private final RestTemplate restTemplate;
    private final Service4Service service4Service;


    private final RetryRegistry retryRegistry;
    private final CircuitBreakerRegistry circuitBreakerRegistry;

    private int requestCount=0;

    @GetMapping("/{service4Id}")
    public ResponseService4 getService4ById(@PathVariable("service4Id") Integer service4Id) {
        log.info("Request No- "+ requestCount++);
        log.info("Inside getService4ById method of Service4Controller");
        Service4 service4 = service4Service.getService4ById(service4Id);
        ResponseService5 responseService5 = getResponseService5(service4.getService4Id());
        return ResponseService4.builder().service5(responseService5)
                .service4Id(service4.getService4Id())
                .service4Message(service4.getService4Message())
                .build();
    }

    private ResponseService5 getResponseService5(Integer service4Id) {

        Retry retry = retryRegistry.retry("service4");
        CircuitBreaker circuitBreaker = circuitBreakerRegistry.circuitBreaker("service4");
        log.info("Inside getResponseService5 method calling MicroService-5 API");
        Supplier<ResponseService5> responseService5Supplier = () -> {
            ResponseEntity<ResponseService5> responseService5ResponseEntity = this.restTemplate.getForEntity(SERVICE5_URL + "/" + service4Id, ResponseService5.class);
            return responseService5ResponseEntity.getBody();
        };
        responseService5Supplier = Decorators.ofSupplier(responseService5Supplier)
                .withCircuitBreaker(circuitBreaker)
                .withRetry(retry)
                .decorate();
        return Try.ofSupplier(responseService5Supplier)
                .recover(throwable -> null).get();
    }

}
