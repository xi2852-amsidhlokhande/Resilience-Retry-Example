package com.amsidh.mvc.controller;

import com.amsidh.mvc.domain.ResponseService5;
import com.amsidh.mvc.entities.Service5;
import com.amsidh.mvc.service.Service5Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/service5")
@Slf4j
public class Service5Controller {

    private final Service5Service service5Service;

    @GetMapping
    public List<ResponseService5> findAllService5() {
        log.info("Inside findAllService5 method of Service5Controller");
        return service5Service.getAllService5().stream()
                .map(service5 -> ResponseService5.builder().service5Id(service5.getService5Id()).service5Message(service5.getService5Message()).build())
                .collect(Collectors.toList());
    }

    @GetMapping("/{service5Id}")
    public ResponseService5 getService5ById(@PathVariable("service5Id") Integer service5Id) {
        log.info("Inside getService5ById method of Service5Controller");
        Service5 service5 = service5Service.getService5ById(service5Id);
        return ResponseService5.builder()
                .service5Id(service5.getService5Id())
                .service5Message(service5.getService5Message())
                .build();
    }

    @PostMapping
    public ResponseService5 saveService5(@RequestBody @Valid Service5 service5) {
        log.info("Inside saveService5 method of Service5Controller");
        Service5 service5Saved = service5Service.saveService5(service5);
        return ResponseService5.builder()
                .service5Id(service5Saved.getService5Id())
                .service5Message(service5Saved.getService5Message())
                .build();
    }
}
