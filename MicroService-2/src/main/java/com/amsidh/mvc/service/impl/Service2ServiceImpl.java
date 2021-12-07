package com.amsidh.mvc.service.impl;

import com.amsidh.mvc.entities.Service2;
import com.amsidh.mvc.repository.Service2Repository;
import com.amsidh.mvc.service.Service2Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class Service2ServiceImpl implements Service2Service {
    private final Service2Repository service2Repository;
    @Override
    public Service2 getService2ById(Integer service2Id) {
        log.info("Inside getService2ById of Service2ServiceImpl class");
        return service2Repository.findById(service2Id).orElseThrow();
    }

}
