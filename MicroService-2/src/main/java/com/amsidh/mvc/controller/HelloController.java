package com.amsidh.mvc.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.cloud.sleuth.instrument.async.TraceableExecutorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
@RestController
@RequestMapping("/service2")
@Slf4j
public class HelloController {

    private final static String SERVICE3_URL = "http://localhost:8083/service3/sayHello3";
    private final RestTemplate restTemplate;
    private final Tracer tracer;
    private final BeanFactory beanFactory;
    private final TraceableExecutorService traceableExecutorService;

    @GetMapping("/sayHello4")
    public String sayHello() {
        log.info("@@@@@@@@@@@@@@@@@@ Inside sayHello method of Service2 @@@@@@@@@@@@@@@@@");

        CompletableFuture<ResponseEntity<String>> responseEntityCompletableFuture = CompletableFuture.supplyAsync(
                () -> {
                    ResponseEntity<String> forEntity = restTemplate.getForEntity(SERVICE3_URL, String.class);
                    log.info("#####################################");
                    return forEntity;
                },
                traceableExecutorService
        );

        return "Service-2 Message-1";
    }
}
