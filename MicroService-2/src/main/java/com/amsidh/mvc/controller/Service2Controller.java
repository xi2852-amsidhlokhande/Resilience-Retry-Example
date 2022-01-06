package com.amsidh.mvc.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/service2")
@Slf4j
public class Service2Controller {

    @GetMapping("/checkProgress")
    public ResponseEntity<String> checkProgress() {
        log.info("Inside checkProgress method of Service2Controller");
        String status = "INPROGRESS";
        return ResponseEntity.ok(status);
    }
}
