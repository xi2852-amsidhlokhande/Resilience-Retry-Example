package com.amsidh.mvc.controller;

import com.amsidh.mvc.domain.ResponseService1;
import com.amsidh.mvc.domain.ResponseService2;
import com.amsidh.mvc.entities.Service1;
import com.amsidh.mvc.exception.predicate.Service1ExceptionPredicate;
import com.amsidh.mvc.service.Service1Service;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.decorators.Decorators;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static java.util.concurrent.TimeUnit.SECONDS;

@RequiredArgsConstructor
@RestController
@RequestMapping("/service1")
@Slf4j
public class Service1Controller {

    private final Service1Service service1Service;

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


    @io.github.resilience4j.retry.annotation.Retry(name = "service1")
    @io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker(name = "service1")
    @GetMapping("/{service1Id}")
    public ResponseService1 getService1ById(@PathVariable("service1Id") Integer service1Id) {
        log.info("Inside getService1ById method of Service1Controller");
        Service1 service1 = service1Service.getService1ById(service1Id);
        return ResponseService1.builder().service2(getResponseService2(service1.getService1Id()))
                .service1Id(service1.getService1Id())
                .service1Message(service1.getService1Message())
                .build();
    }

    @GetMapping("/supplier/{service1Id}")
    public ResponseService1 getService1ByIdSupplier(@PathVariable("service1Id") Integer service1Id) {
        log.info("Inside getService1ById method of Service1Controller");
        Service1 service1 = service1Service.getService1ById(service1Id);
        return ResponseService1.builder().service2(getResponseService2Test(service1.getService1Id()))
                .service1Id(service1.getService1Id())
                .service1Message(service1.getService1Message())
                .build();
    }


    @GetMapping("/supplier2/{service1Id}")
    public ResponseService1 getService1ByIdSupplier2(@PathVariable("service1Id") Integer service1Id) {
        log.info("Inside getService1ById method of Service1Controller");
        Service1 service1 = service1Service.getService1ById(service1Id);
        return ResponseService1.builder().service2(getResponseService2Test_1(service1.getService1Id()))
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

    public ResponseService2 getResponseService2Test(Integer service2Id) {
        Retry retry = Retry.ofDefaults("service1");
        CircuitBreaker circuitBreaker = CircuitBreaker.ofDefaults("service1");
        Supplier<ResponseService2> responseService2Supplier = () -> getResponseService2(service2Id);

        // Decorate your call to getResponseService2(service2Id)
        // with a Bulkhead, CircuitBreaker and Retry
        // **note: you will need the resilience4j-all dependency for this
        Supplier<ResponseService2> decoratedSupplier = Decorators.ofSupplier(responseService2Supplier)
                .withCircuitBreaker(circuitBreaker)
                .withRetry(retry)
                .decorate();
        // they know what to do with it
        circuitBreaker.getEventPublisher()
                .onError(event -> log.info(event.toString()));

        responseService2Supplier = Retry.decorateSupplier(retry, responseService2Supplier);


        return Try.ofSupplier(responseService2Supplier)
                .recover(throwable -> null).get();
    }

    public ResponseService2 getResponseService2Test_1(Integer service2Id) {

        Predicate<Throwable> httpServerErrorPredicate = new Predicate<Throwable>() {
            @Override
            public boolean test(Throwable throwable) {
                if (throwable instanceof HttpServerErrorException) {
                    HttpServerErrorException httpServerErrorException = (HttpServerErrorException) throwable;
                    if (httpServerErrorException.getStatusCode().is4xxClientError()) {
                        return true;
                    }
                }else if(throwable instanceof ResourceAccessException){
                    ResourceAccessException resourceAccessException = (ResourceAccessException) throwable;
                    return true;
                }
                return false;
            }
        };

        RetryConfig config = RetryConfig.custom()
                .maxAttempts(3)
                .waitDuration(Duration.of(3, ChronoUnit.SECONDS))
                .retryOnException(httpServerErrorPredicate)
                .retryExceptions(HttpServerErrorException.class)
                .build();

        Retry retry = Retry.of("myCustomConfig", config);
        CircuitBreaker circuitBreaker = CircuitBreaker.ofDefaults("service1");

        Supplier<ResponseService2> responseService2Supplier = () -> getResponseService2(service2Id);

        // Decorate your call to getResponseService2(service2Id)
        // with a Bulkhead, CircuitBreaker and Retry
        // **note: you will need the resilience4j-all dependency for this
        Supplier<ResponseService2> decoratedSupplier = Decorators.ofSupplier(responseService2Supplier)
                .withCircuitBreaker(circuitBreaker)
                .withRetry(retry)
                .decorate();
        // they know what to do with it
        circuitBreaker.getEventPublisher()
                .onError(event -> log.info(event.toString()));

        responseService2Supplier = Retry.decorateSupplier(retry, responseService2Supplier);


        return Try.ofSupplier(responseService2Supplier)
                .recover(throwable -> null).get();
    }


    private ResponseService2 getResponseService2(Integer service2Id) {

        log.info("Inside getResponseService2 method calling MicroService-2 API");
        ResponseEntity<ResponseService2> responseService2ResponseEntity = this.restTemplate.getForEntity(SERVICE2_URL + "/" + service2Id, ResponseService2.class);
        log.info("Inside getResponseService2 method and Response received from MicroService-2");
        return responseService2ResponseEntity.getBody();
    }

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
