package com.amsidh.mvc.controller;

import com.amsidh.mvc.domain.ResponseService2;
import com.amsidh.mvc.domain.ResponseService2;
import com.amsidh.mvc.domain.ResponseService3;
import com.amsidh.mvc.entities.Service2;
import com.amsidh.mvc.service.Service2Service;
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
@RequestMapping("/service2")
@Slf4j
public class Service2Controller {

    private final static String SERVICE3_URL = "http://localhost:8083/service3";
    private final RestTemplate restTemplate;
    private final Service2Service service2Service;


    private final RetryRegistry retryRegistry;
    private final CircuitBreakerRegistry circuitBreakerRegistry;

    private int requestCount = 0;
    @GetMapping("/{service2Id}")
    public ResponseService2 getService2ById(@PathVariable("service2Id") Integer service2Id) {
        log.info("Request No- " + requestCount++);
        Service2 service2 = service2Service.getService2ById(service2Id);
        ResponseService3 responseService3 = getResponseService3(service2.getService2Id());
        return ResponseService2.builder().service3(responseService3)
                .service2Id(service2.getService2Id())
                .service2Message(service2.getService2Message())
                .build();
    }

    private ResponseService3 getResponseService3(Integer service2Id) {

        Retry retry = retryRegistry.retry("service2");
        CircuitBreaker circuitBreaker = circuitBreakerRegistry.circuitBreaker("service2");
        log.info("Calling MicroService-3 API");
        Supplier<ResponseService3> responseService3Supplier = () -> {
            ResponseEntity<ResponseService3> responseService3ResponseEntity= this.restTemplate.getForEntity(SERVICE3_URL + "/" + service2Id, ResponseService3.class);
            return responseService3ResponseEntity.getBody();
        };
        responseService3Supplier = Decorators.ofSupplier(responseService3Supplier)
                .withCircuitBreaker(circuitBreaker)
                .withRetry(retry)
                .decorate();
        return Try.ofSupplier(responseService3Supplier)
                .recover(throwable -> null).get();
    }

}
