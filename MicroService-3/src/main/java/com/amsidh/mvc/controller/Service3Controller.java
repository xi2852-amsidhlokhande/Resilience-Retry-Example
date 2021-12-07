package com.amsidh.mvc.controller;

import com.amsidh.mvc.domain.ResponseService3;
import com.amsidh.mvc.domain.ResponseService3;
import com.amsidh.mvc.domain.ResponseService3;
import com.amsidh.mvc.domain.ResponseService4;
import com.amsidh.mvc.entities.Service3;
import com.amsidh.mvc.service.Service3Service;
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
@RequestMapping("/service3")
@Slf4j
public class Service3Controller {

    private final static String SERVICE4_URL = "http://localhost:8084/service4";
    private final RestTemplate restTemplate;
    private final Service3Service service3Service;


    private final RetryRegistry retryRegistry;
    private final CircuitBreakerRegistry circuitBreakerRegistry;

    private int requestCount = 0;
    @GetMapping("/{service3Id}")
    public ResponseService3 getService3ById(@PathVariable("service3Id") Integer service3Id) {
        log.info("Request No- " + requestCount++);
        Service3 service3 = service3Service.getService3ById(service3Id);
        ResponseService4 responseService4 = getResponseService4(service3.getService3Id());
        return ResponseService3.builder().service4(responseService4)
                .service3Id(service3.getService3Id())
                .service3Message(service3.getService3Message())
                .build();
    }

    private ResponseService4 getResponseService4(Integer service3Id) {

        Retry retry = retryRegistry.retry("service3");
        CircuitBreaker circuitBreaker = circuitBreakerRegistry.circuitBreaker("service3");
        log.info("Calling MicroService-4 API");
        Supplier<ResponseService4> responseService4Supplier = () -> {
            ResponseEntity<ResponseService4> responseService4ResponseEntity = this.restTemplate.getForEntity(SERVICE4_URL + "/" + service3Id, ResponseService4.class);
            return responseService4ResponseEntity.getBody();
        };
        responseService4Supplier = Decorators.ofSupplier(responseService4Supplier)
                .withCircuitBreaker(circuitBreaker)
                .withRetry(retry)
                .decorate();
        return Try.ofSupplier(responseService4Supplier)
                .recover(throwable -> null).get();
    }

}
