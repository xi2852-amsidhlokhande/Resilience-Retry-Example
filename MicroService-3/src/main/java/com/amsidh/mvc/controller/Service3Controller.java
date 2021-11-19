package com.amsidh.mvc.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@RestController
@RequestMapping("/service3")
@Slf4j
public class Service3Controller {

    private final RestTemplate restTemplate;

    @GetMapping("/sayHello3")
    public String sayHello() {
        log.info("Service3 sayHello method called");
        return "Service-3 Message-1";
    }


}
