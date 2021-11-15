package com.amsidh.mvc.service.impl;

import com.amsidh.mvc.entities.Service3;
import com.amsidh.mvc.repository.Service3Repository;
import com.amsidh.mvc.service.Service3Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class Service3ServiceImpl implements Service3Service {
    private final Service3Repository service3Repository;
    @Override
    public Service3 getService3ById(Integer service3Id) {
        log.info("Inside getService3ById method Service3ServiceImpl class");
        return service3Repository.findById(service3Id).orElseThrow();
    }

}
