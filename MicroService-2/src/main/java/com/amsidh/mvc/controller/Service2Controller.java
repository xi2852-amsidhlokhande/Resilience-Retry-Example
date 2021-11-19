package com.amsidh.mvc.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static net.logstash.logback.argument.StructuredArguments.kv;

@RequiredArgsConstructor
@RestController
@RequestMapping("/service2")
@Slf4j
public class Service2Controller {

    private final static String SERVICE3_URL = "http://localhost:8083/service3/sayHello3";
    private final RestTemplate restTemplate;

    @GetMapping("/sayHello2")
    public String sayHello() {
        log.info("@@@@@@@@@@@@@@@@@@ Inside sayHello method of Service2 @@@@@@@@@@@@@@@@@");
       /* myMethod(() -> {
            log.info("========================Calling Service3=======================");
            ResponseEntity<String> responseEntity = restTemplate.getForEntity(SERVICE3_URL, String.class);
            log.info("Response from Service3 is {}", responseEntity.getBody(), kv("status", responseEntity.getStatusCodeValue()));
        });*/
        return "Service-2 Message-1";
    }

    @GetMapping("/sayHello3")
    public String sayHello3() {
        Map<String, String> previous = MDC.getCopyOfContextMap();
        CompletableFuture<ResponseEntity<String>> completableFuture = CompletableFuture.supplyAsync(() -> {
            MDC.setContextMap(previous);
            try {
                ResponseEntity<String> responseEntity = restTemplate.getForEntity(SERVICE3_URL, String.class);
                log.info("Response from Service3 is {}", responseEntity.getBody(), kv("status", responseEntity.getStatusCodeValue()));
                return responseEntity;
            } finally {
                MDC.clear();
            }
        });

        try {
            String body = completableFuture.get().getBody();
            log.info("Request Body: " + body);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return "Service-2 Message-3";
    }

    public CompletableFuture<ResponseEntity<String>> myMethod() {
        Map<String, String> previous = MDC.getCopyOfContextMap();
        return CompletableFuture.supplyAsync(() -> {
            MDC.setContextMap(previous);
            try {
                ResponseEntity<String> responseEntity = restTemplate.getForEntity(SERVICE3_URL, String.class);
                log.info("Response from Service3 is {}", responseEntity.getBody(), kv("status", responseEntity.getStatusCodeValue()));
                return responseEntity;
            } finally {
                MDC.clear();
            }
        });
    }
}
