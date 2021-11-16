package com.amsidh.mvc.service.impl;

import com.amsidh.mvc.entities.Service1;
import com.amsidh.mvc.repository.Service1Repository;
import com.amsidh.mvc.service.Service1Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class Service1ServiceImpl implements Service1Service {
    private final Service1Repository service1Repository;

    @Override
    public Service1 getService1ById(Integer service1Id) {
        log.info("Inside getService1ById method Service1ServiceImpl class");
        return service1Repository.findById(service1Id).orElseThrow();
    }

}
