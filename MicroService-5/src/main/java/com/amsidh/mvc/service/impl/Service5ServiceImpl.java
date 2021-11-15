package com.amsidh.mvc.service.impl;

import com.amsidh.mvc.entities.Service5;
import com.amsidh.mvc.exception.Service5NotFound;
import com.amsidh.mvc.repository.Service5Repository;
import com.amsidh.mvc.service.Service5Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class Service5ServiceImpl implements Service5Service {
    private final Service5Repository service5Repository;

    @Override
    public Service5 getService5ById(Integer service5Id) {
        log.info("Inside getService5ById method Service5ServiceImpl");
        return service5Repository.findById(service5Id).orElseThrow(() -> new Service5NotFound("Service5 not found with service5Id " + service5Id));
    }
}
