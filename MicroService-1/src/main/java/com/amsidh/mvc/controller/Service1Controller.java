package com.amsidh.mvc.controller;

import com.amsidh.mvc.domain.ResponseService1;
import com.amsidh.mvc.domain.ResponseService2;
import com.amsidh.mvc.entities.Service1;
import com.amsidh.mvc.service.Service1Service;
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
@RequestMapping("/service1")
@Slf4j
public class Service1Controller {

    private final static String SERVICE2_URL = "http://localhost:8082/service2";
    private final RestTemplate restTemplate;
    private final Service1Service service1Service;


    private final RetryRegistry retryRegistry;
    private final CircuitBreakerRegistry circuitBreakerRegistry;

    private int requestCount = 0;

    @GetMapping("/{service1Id}")
    public ResponseService1 getService1ById(@PathVariable("service1Id") Integer service1Id) {
        log.info("Request No- " + requestCount++);
        Service1 service1 = service1Service.getService1ById(service1Id);
        ResponseService2 responseService2 = getResponseService2(service1.getService1Id());
        return ResponseService1.builder().service2(responseService2)
                .service1Id(service1.getService1Id())
                .service1Message(service1.getService1Message())
                .build();
    }

    private ResponseService2 getResponseService2(Integer service2Id) {

        Retry retry = retryRegistry.retry("service1");
        CircuitBreaker circuitBreaker = circuitBreakerRegistry.circuitBreaker("service1");
        log.info("Calling MicroService-2 API");
        Supplier<ResponseService2> responseService2Supplier = () -> {
            ResponseEntity<ResponseService2> responseService2ResponseEntity = this.restTemplate.getForEntity(SERVICE2_URL + "/" + service2Id, ResponseService2.class);
            return responseService2ResponseEntity.getBody();
        };
        responseService2Supplier = Decorators.ofSupplier(responseService2Supplier)
                .withCircuitBreaker(circuitBreaker)
                .withRetry(retry)
                .decorate();
        return Try.ofSupplier(responseService2Supplier)
                .recover(throwable -> null).get();
    }

}
